package com.justice.hms.Service;

import com.justice.hms.Model.Hostel;
import com.justice.hms.Model.Room;
import com.justice.hms.Repository.BookingRepository;
import com.justice.hms.Repository.HostelRepository;
import com.justice.hms.Repository.RoomRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HostelService {

    private final HostelRepository hostelRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public HostelService(HostelRepository hostelRepository, RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.hostelRepository = hostelRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Hostel createHostel(Hostel hostel) {

        Hostel savedHostel = hostelRepository.save(hostel);

        for (int i = 1; i <= hostel.getNumberOfRooms(); i++) {

            Room room = new Room();

            room.setRoomNumber(hostel.getName() + i);
            room.setCapacity(hostel.getRoomCapacity());
            room.setOccupiedSpaces(0);
            room.setHostel(savedHostel);

            roomRepository.save(room);
        }

        return savedHostel;
    }

    public List<Hostel> findAll() {
        return hostelRepository.findAll();
    }

    public Hostel findById(Integer id) {

        return hostelRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Hostel not found"));
    }

    @Transactional
    public void updateHostel(Integer id, Hostel updatedHostel) {

        Hostel existingHostel = findById(id);

        List<Room> rooms =
                roomRepository.findByHostelIdOrderByIdAsc(id);

        int oldRoomCount = rooms.size();
        int newRoomCount = updatedHostel.getNumberOfRooms();

        String oldHostelName = existingHostel.getName();

        if (newRoomCount < oldRoomCount) {

            for (int i = newRoomCount; i < oldRoomCount; i++) {

                Room roomToRemove = rooms.get(i);

                if (bookingRepository.existsByRoomId(roomToRemove.getId())) {

                    throw new IllegalArgumentException(
                            "Cannot reduce the number of rooms because "
                            + roomToRemove.getRoomNumber()
                            + " has an active booking"
                    );
                }
            }

         
            for (int i = oldRoomCount - 1;
                 i >= newRoomCount;
                 i--) {

                roomRepository.delete(rooms.get(i));
            }
        }

        existingHostel.setName(updatedHostel.getName());
        existingHostel.setLocation(updatedHostel.getLocation());
        existingHostel.setNumberOfRooms(newRoomCount);
        existingHostel.setRoomCapacity(updatedHostel.getRoomCapacity());

        hostelRepository.save(existingHostel);

        rooms = roomRepository.findByHostelIdOrderByIdAsc(id);


        for (int i = 0; i < rooms.size(); i++) {

            Room room = rooms.get(i);

            room.setRoomNumber(existingHostel.getName() + (i + 1));
            room.setCapacity(existingHostel.getRoomCapacity());

            roomRepository.save(room);
        }

        if (newRoomCount > oldRoomCount) {

            for (int i = oldRoomCount + 1;
                i <= newRoomCount;
                i++) {

                Room room = new Room();

                room.setRoomNumber(existingHostel.getName() + i);
                room.setCapacity(existingHostel.getRoomCapacity());
                room.setOccupiedSpaces(0);
                room.setHostel(existingHostel);

                roomRepository.save(room);
            }
        }
    }

    @Transactional
    public void deleteHostel(Integer id) {

        Hostel hostel = findById(id);

    
        if (bookingRepository.existsByRoom_Hostel_Id(id)) {

            throw new IllegalArgumentException(
                    "Cannot delete this hostel because "
                    + "it has rooms with active bookings"
            );
        }

        List<Room> rooms =
                roomRepository.findByHostelId(id);

    
        roomRepository.deleteAll(rooms);

        hostelRepository.delete(hostel);
    }
}
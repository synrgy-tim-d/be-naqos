package com.binar.kelompokd;

import com.binar.kelompokd.enums.Condition;
import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.enums.RoomType;
import com.binar.kelompokd.models.entity.*;
import com.binar.kelompokd.repos.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	/**
	 * comment this if data already persisted
	 */
	@Bean
	@Transactional
	CommandLineRunner commandLineRunner(AddressRepository addressRepository, KostRepository kostRepository, CityRepository cityRepository, ImageRepository imageRepository, KostRoomRepository kostRoomRepository, ProvinceRepository provinceRepository, RoomFacilityRepository roomFacilityRepository) {
		return args -> {
//			City city = City.builder()
//					.id(1)
//					.city("Jakarta")
//					.build();
//
//			cityRepository.save(city);

//			Province province = Province.builder()
//					.id(1)
//					.province("DKI Jakarta")
//					.build();
//
//			provinceRepository.save(province);

//			Address address = Address.builder()
//					.id(1)
//					.address("Kebagusan 1 Street")
//					.city(city)
//					.province(province)
//					.district("South Jakarta")
//					.latitude(-6.2)
//					.longitude(106.81666)
//					.postalCode("10110")
//					.subdistrict("Jagakarsa")
//					.build();
//
//			addressRepository.save(address);

			Image image1 = Image.builder()
					.id(1)
					.fileLocation("www.google.com")
					.build();

			imageRepository.save(image1);

			Image image2 = Image.builder()
					.id(2)
					.fileLocation("www.facebook.com")
					.build();

			imageRepository.save(image2);

			Image image3 = Image.builder()
					.id(3)
					.fileLocation("www.instagram.com")
					.build();

			imageRepository.save(image3);

			Integer[] roomIds = {1,2,3};

//			Kost kost = Kost.builder()
//					.name("Kost Alamanda")
//					.description("Kost for IT Talents")
//					.kostType(KostType.KOS_CAMPURAN)
//					.location(address)
//					.isAvailable(true)
//					.roomId(roomIds)
//					.build();
//
//			kost.setCreatedAt(new Date());
//
//			kostRepository.save(kost);

			RoomFacility roomFacility1 = RoomFacility.builder()
					.condition(Condition.BARU)
					.id(1)
					.isActive(true)
					.name("AC")
					.build();

			roomFacilityRepository.save(roomFacility1);

			RoomFacility roomFacility2 = RoomFacility.builder()
					.condition(Condition.BARU)
					.id(2)
					.isActive(true)
					.name("Water Heater")
					.build();

			roomFacilityRepository.save(roomFacility2);

			RoomFacility roomFacility3 = RoomFacility.builder()
					.condition(Condition.BARU)
					.id(3)
					.isActive(true)
					.name("Double Bed")
					.build();

			roomFacilityRepository.save(roomFacility3);

			Integer[] imageIds = {1,2,3};

			Integer[] facilityIds = {1,2,3};

			KostRoom kostRoom1 = KostRoom.builder()
					.id(1)
					.facilityId(facilityIds)
					.imageId(imageIds)
					.rules("No Rules")
					.roomType(RoomType.FULL_FURNISHED)
					.isAvailable(true)
					.pricePerDaily(150000d)
					.pricePerWeekly(900000d)
					.pricePerMonthly(1500000d)
					.build();

			kostRoomRepository.save(kostRoom1);

			KostRoom kostRoom2 = KostRoom.builder()
					.id(2)
					.facilityId(facilityIds)
					.imageId(imageIds)
					.rules("No Rules")
					.roomType(RoomType.FULL_FURNISHED)
					.isAvailable(true)
					.pricePerDaily(200000d)
					.pricePerWeekly(1000000d)
					.pricePerMonthly(1700000d)
					.build();

			kostRoomRepository.save(kostRoom2);

			KostRoom kostRoom3 = KostRoom.builder()
					.id(3)
					.facilityId(facilityIds)
					.imageId(imageIds)
					.rules("No Rules")
					.roomType(RoomType.FULL_FURNISHED)
					.isAvailable(true)
					.pricePerDaily(250000d)
					.pricePerWeekly(1200000d)
					.pricePerMonthly(2000000d)
					.build();

			kostRoomRepository.save(kostRoom3);
		};
	}
}


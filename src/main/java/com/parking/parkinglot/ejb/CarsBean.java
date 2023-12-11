package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars(){
        LOG.info("findAllCars");
        try{
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> carDtoList = new ArrayList<>() {
        };

        for (Car car : cars) {
            Long carId = car.getId();
            String carLicensePlate = car.getLicencePlate();
            String carParkingSpot = car.getParkingSpot();
            String carOwner = car.getOwner().getUsername();
            CarDto carDto = new CarDto(carId,carLicensePlate,carParkingSpot,carOwner);


            carDtoList.add(carDto);
        }

        return carDtoList;
    }

    private CarDto copyCarToDto(Car car) {
        Long carId = car.getId();
        String carLicensePlate = car.getLicencePlate();
        String carParkingSpot = car.getParkingSpot();
        String carOwner = car.getOwner().getUsername();
        return new CarDto(carId, carLicensePlate, carParkingSpot, carOwner);
    }


    public CarDto findById(Long carId) {
        LOG.info("findById");
        try {
            Car car = entityManager.find(Car.class, carId);
            if (car != null) {
                return copyCarToDto(car);
            } else {
                return null; // Car not found
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public void createCar(String licensePlate, String parkingSpot, Long userId){
        LOG.info("createCar");

        Car car = new Car();
        car.setLicencePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);

        entityManager.persist(car);
    }

    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userId){
        LOG.info("updateCar");

        Car car=entityManager.find(Car.class, carId);
        car.setLicencePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        //remove this car from old owner
        User oldUser = car.getOwner();
        oldUser.getCars().remove(car);

        //add car to new owner
        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);
    }

    public void deleteCarsByIds(Collection<Long> carIds){
        LOG.info("DeleteCarsByIds");

        for(Long carId : carIds){
            Car car = entityManager.find(Car.class, carId);
            entityManager.remove(car);
        }
    }
}

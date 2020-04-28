package ru.itis.shop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.shop.dto.BasketDto;
import ru.itis.shop.dto.GoodDto;
import ru.itis.shop.models.Basket;
import ru.itis.shop.models.Good;
import ru.itis.shop.models.User;
import ru.itis.shop.repositories.BasketRepository;
import ru.itis.shop.repositories.UsersRepository;
import ru.itis.shop.services.BasketService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class BasketServiceImpl implements BasketService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Override
    public List<BasketDto> getBasket(String email) {
        Optional<User> optionalUser = usersRepository.find(email);
        if (optionalUser.isPresent()) {
            System.out.println(optionalUser.get().getBasket());
            Set<Basket> basket = optionalUser.get().getBasket();
            if (basket == null){
                return new ArrayList<>();
            }
            List<BasketDto> basketDtoList = new ArrayList<>();
            basket.forEach(bask -> {
                basketDtoList.add(BasketDto.builder()
                        .id(bask.getId())
                        .goodDto(GoodDto.from(bask.getGood()))
                        .quantityGood(bask.getQuantityGood())
                        .build());
            });
            return basketDtoList;
        } else throw new IllegalStateException("Пользователь не найден");
    }

    @Override
    public void addBasket(String email, BasketDto basketDto) {
        Good good = Good.builder()
                .id(basketDto.getGoodDto().getId())
                .build();
        Optional<User> optionalPerson = usersRepository.find(email);
        if (optionalPerson.isPresent()) {
            Basket basket = Basket.builder()
                    .user(optionalPerson.get())
                    .good(good)
                    .quantityGood(basketDto.getQuantityGood())
                    .build();
            Optional<Basket> basketOptional = basketRepository.findByUserIdAndGoodId(basket.getUser().getId(),
                    basket.getGood().getId());
            if (!basketOptional.isPresent()) {
                basketRepository.save(basket);
            } else if (!basketDto.getQuantityGood().equals(basketOptional.get().getQuantityGood())) {
                basketRepository.updateQuantity(basketOptional.get().getId(), basket.getQuantityGood());
            } else throw new IllegalStateException("Товар уже находиться в корзине");
    } else throw new IllegalStateException("Пользователь не найден");

}

    @Override
    public void deleteBasket(String email, BasketDto basketDto) {
        Optional<User> optionalPerson = usersRepository.find(email);
        if (optionalPerson.isPresent()) {
            basketRepository.delete(basketDto.getId());
        } else throw new IllegalStateException("Пользователь не найден");
    }

    @Override
    public void deleteAllBasket(String email) {
        Optional<User> optionalPerson = usersRepository.find(email);
        if (optionalPerson.isPresent()) {
            basketRepository.deleteUserAll(optionalPerson.get().getId());
        } else throw new IllegalStateException("Пользователь не найден");
    }
}

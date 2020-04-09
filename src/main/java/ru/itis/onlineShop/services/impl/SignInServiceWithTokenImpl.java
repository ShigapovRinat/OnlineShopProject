package ru.itis.onlineShop.services.impl;

//@Component
//public class SignInServiceWithTokenImpl implements SignInService {
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//
//    @Autowired
//    private PersonsRepository personsRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public TokenDto signIn(SignInDto signInDto) {
//        // получаем пользователя по его email
//        Optional<Person> personOptional = personsRepository.find(signInDto.getEmail());
//        // если у меня есть этот пользвователь
//        if (personOptional.isPresent()) {
//            // получаем его
//            Person person = personOptional.get();
//            // если пароль подходит
//            if (passwordEncoder.matches(signInDto.getPassword(), person.getHashPassword())) {
//                // создаем токен
//                String token = Jwts.builder()
//                        .setSubject(person.getId().toString()) // id пользователя
//                        .claim("email", person.getEmail()) // имя
//                        .claim("role", person.getRole().name()) // роль
//                        .signWith(SignatureAlgorithm.HS256, secret) // подписываем его с нашим secret
//                        .compact(); // преобразовали в строку
//                return new TokenDto(token);
//            } else throw new AccessDeniedException("Wrong email/password");
//        } else throw new AccessDeniedException("Person not found");
//    }
//}
//

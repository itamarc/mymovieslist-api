package io.itamarc.mymovieslistapi.bootstrap;

import java.time.LocalDateTime;
import java.util.*;

import io.itamarc.mymovieslistapi.repositories.MovieGenreRepository;
import io.itamarc.mymovieslistapi.services.MoviesListConverter;
import io.itamarc.mymovieslistapi.services.MoviesListService;
import io.itamarc.mymovieslistapi.services.UserService;
import io.itamarc.mymovieslistapi.transfer.MovieGenrePayload;
import io.itamarc.mymovieslistapi.transfer.MoviePayload;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;
import io.itamarc.mymovieslistapi.transfer.UserPayload;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.itamarc.mymovieslistapi.model.AuthProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile({"test", "dev"})
public class DataLoader implements CommandLineRunner {
    UserService userService;
    MoviesListService moviesListService;
    MovieGenreRepository movieGenreRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserService userService,
                      MoviesListService moviesListService,
                      MovieGenreRepository movieGenreRepository,
                      PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.moviesListService = moviesListService;
        this.movieGenreRepository = movieGenreRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userService.findAll().isEmpty()) {
            log.debug("Loading data in bootstrap...");
            loadData();
            log.debug("Data fully loaded in bootstrap.");
        }
    }

    private void loadData() {
        UserPayload user1 = userService.save(
            UserPayload.builder()
                .name("Itamar C")
                .email("itamarc@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .imageUrl("https://avatars.githubusercontent.com/u/19577272?v=4")
                .registered(LocalDateTime.now())
                .provider(AuthProvider.local)
                .emailVerified(true)
                .build()
        );

        UserPayload user2 = userService.save(
            UserPayload.builder()
                .name("John C")
                .email("john.constantine@realhell.com")
                .password(passwordEncoder.encode("123456"))
                .imageUrl("https://www.superherodb.com/pictures2/portraits/10/100/718.jpg")
                .registered(LocalDateTime.now())
                .provider(AuthProvider.local)
                .emailVerified(true)
                .build()
        );

        Map<Long, MovieGenrePayload> genres = loadGenres();

        Set<MoviesListPayload> moviesListsData = new LinkedHashSet<>();
        moviesListsData.add(MoviesListPayload.builder().user(user1).title("Action Movies").build()); // 28
        moviesListsData.add(MoviesListPayload.builder().user(user1).title("Scary Movies").build()); // 27
        moviesListsData.add(MoviesListPayload.builder().user(user1).title("Sci-fi Movies").build()); // 878
        moviesListsData.add(MoviesListPayload.builder().user(user1).title("War Movies").build()); // 10752
        moviesListsData.add(MoviesListPayload.builder().user(user1).title("Adventure Movies").build()); // 12
        moviesListsData.add(MoviesListPayload.builder().user(user1).title("Animations").build()); // 16
        moviesListsData.add(MoviesListPayload.builder().user(user1).title("Fantasy Movies").build()); // 14
        moviesListsData.add(MoviesListPayload.builder().user(user2).title("Christmas Movies").build()); // 10751 (Family)
        moviesListsData.add(MoviesListPayload.builder().user(user2).title("Horror Movies").build()); // 27
        moviesListsData.add(MoviesListPayload.builder().user(user2).title("Westerns").build()); // 37
        moviesListsData.add(MoviesListPayload.builder().user(user2).title("Mystery Movies").build()); // 9648
        moviesListsData.add(MoviesListPayload.builder().user(user2).title("Active Movies").build()); // 28

        Map<String, Integer> moviesList2Genre = new HashMap<>();
        moviesList2Genre.put("Action Movies", 28);
        moviesList2Genre.put("Scary Movies", 27);
        moviesList2Genre.put("Sci-fi Movies", 878);
        moviesList2Genre.put("War Movies", 10752);
        moviesList2Genre.put("Adventure Movies", 12);
        moviesList2Genre.put("Animations", 16);
        moviesList2Genre.put("Fantasy Movies", 14);
        moviesList2Genre.put("Christmas Movies", 10751); // Family
        moviesList2Genre.put("Horror Movies", 27);
        moviesList2Genre.put("Westerns", 37);
        moviesList2Genre.put("Mystery Movies", 9648);
        moviesList2Genre.put("Active Movies", 28); // Action

        Map<Integer, Set<MoviePayload>> moviesMap = getMoviesByGenre(genres);

        for (MoviesListPayload moviesList : moviesListsData) {
            moviesList.setMovies(moviesMap.get(moviesList2Genre.get(moviesList.getTitle())));
        }
        saveMoviesLists(moviesListsData);
    }

    private Map<Long, MovieGenrePayload> loadGenres() {
        Map<Long, MovieGenrePayload> genres = new HashMap<>();
        genres.put(28L, MovieGenrePayload.builder().id(28L).name("Action").build());
        genres.put(12L, MovieGenrePayload.builder().id(12L).name("Adventure").build());
        genres.put(16L, MovieGenrePayload.builder().id(16L).name("Animation").build());
        genres.put(35L, MovieGenrePayload.builder().id(35L).name("Comedy").build());
        genres.put(80L, MovieGenrePayload.builder().id(80L).name("Crime").build());
        genres.put(99L, MovieGenrePayload.builder().id(99L).name("Documentary").build());
        genres.put(18L, MovieGenrePayload.builder().id(18L).name("Drama").build());
        genres.put(10751L, MovieGenrePayload.builder().id(10751L).name("Family").build());
        genres.put(14L, MovieGenrePayload.builder().id(14L).name("Fantasy").build());
        genres.put(36L, MovieGenrePayload.builder().id(36L).name("History").build());
        genres.put(27L, MovieGenrePayload.builder().id(27L).name("Horror").build());
        genres.put(10402L, MovieGenrePayload.builder().id(10402L).name("Music").build());
        genres.put(9648L, MovieGenrePayload.builder().id(9648L).name("Mystery").build());
        genres.put(10749L, MovieGenrePayload.builder().id(10749L).name("Romance").build());
        genres.put(878L, MovieGenrePayload.builder().id(878L).name("Science Fiction").build());
        genres.put(10770L, MovieGenrePayload.builder().id(10770L).name("TV Movie").build());
        genres.put(53L, MovieGenrePayload.builder().id(53L).name("Thriller").build());
        genres.put(10752L, MovieGenrePayload.builder().id(10752L).name("War").build());
        genres.put(37L, MovieGenrePayload.builder().id(37L).name("Western").build());
        genres.forEach((id, movieGenrePayload) -> movieGenreRepository.save(
                MoviesListConverter.movieGenrePayloadToMovieGenre(movieGenrePayload))
        );
        log.debug("Movies Genres loaded.");
        return genres;
    }

    private Set<MoviesListPayload> saveMoviesLists(Set<MoviesListPayload> moviesListsData) {
        Set<MoviesListPayload> savedMoviesLists = new LinkedHashSet<>();
        moviesListsData.forEach((moviesList -> {
            moviesList.setCreated(LocalDateTime.now());
            moviesList.setUpdated(LocalDateTime.now());
            MoviesListPayload savedMoviesList = moviesListService.newMoviesList(moviesList);
            savedMoviesLists.add(savedMoviesList);
        }));
        return savedMoviesLists;
    }

    private Map<Integer, Set<MoviePayload>> getMoviesByGenre(Map<Long, MovieGenrePayload> genres) {
        Map<Integer, Set<MoviePayload>> map = new HashMap<>();
        Set<MoviePayload> movies;

        Set<MovieGenrePayload> genresMap;
        /* Genre: Action */
        movies = new LinkedHashSet<>();
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Spider-Man: No Way Home")
                .description("Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Uncharted")
                .description("A young street-smart, Nathan Drake and his wisecracking partner Victor �Sully� Sullivan embark on a dangerous pursuit of �the greatest treasure never found� while also tracking clues that may lead to Nathan�s long-lost brother.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/sqLowacltbZLoCa4KYye64RvvdQ.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("Sonic the Hedgehog 2")
                .description("After settling in Green Hills, Sonic is eager to prove he has what it takes to be a true hero. His test comes when Dr. Robotnik returns, this time with a new partner, Knuckles, in search for an emerald that has the power to destroy civilizations. Sonic teams up with his own sidekick, Tails, and together they embark on a globe-trotting journey to find the emerald before it falls into the wrong hands.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Yaksha: Ruthless Operations")
                .description("Nicknamed after a human-devouring spirit, the ruthless leader of an overseas black ops team takes up a dangerous mission in a city riddled with spies.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/7MDgiFOPUCeG74nQsMKJuzTJrtc.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Moonfall")
                .description("A mysterious force knocks the moon from its orbit around Earth and sends it hurtling on a collision course with life as we know it.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/odVv1sqVs0KxBXiA8bhIBlPgalx.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("All the Old Knives")
                .description("When the CIA discovers one of its agents leaked information that cost more than 100 people their lives, veteran operative Henry Pelham is assigned to root out the mole with his former lover and colleague Celia Harrison.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/g4tMniKxol1TBJrHlAtiDjjlx4Q.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Blacklight")
                .description("Travis Block is a shadowy Government agent who specializes in removing operatives whose covers have been exposed. He then has to uncover a deadly conspiracy within his own ranks that reaches the highest echelons of power.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/bv9dy8mnwftdY2j6gG39gCfSFpV.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("War of the Worlds: Annihilation")
                .description("A mother and son find themselves faced with a brutal alien invasion where survival will depend on discovering the unthinkable truth about the enemy.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/9eiUNsUAw2iwVyMeXNNiNQQad4E.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("The Adam Project")
                .description("After accidentally crash-landing in 2022, time-traveling fighter pilot Adam Reed teams up with his 12-year-old self on a mission to save the future.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/wFjboE0aFZNbVOF05fzrka9Fqyx.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Gold")
                .description("In the not-too-distant future, two drifters traveling through the desert stumble across the biggest gold nugget ever found and the dream of immense wealth and greed takes hold. They hatch a plan to excavate their bounty, with one man leaving to secure the necessary tools while the other remains with the gold. The man who remains must endure harsh desert elements, ravenous wild dogs, and mysterious intruders, while battling the sinking suspicion that he has been abandoned to his fate.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/ejXBuNLvK4kZ7YcqeKqUWnCxdJq.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Black Crab")
                .description("To end an apocalyptic war and save her daughter, a reluctant soldier embarks on a desperate mission to cross a frozen sea carrying a top-secret cargo.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/mcIYHZYwUbvhvUt8Lb5nENJ7AlX.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(80L));
        movies.add(MoviePayload.builder()
                .title("Restless")
                .description("After going to extremes to cover up an accident, a corrupt cop's life spirals out of control when he starts receiving threats from a mysterious witness.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/9MP21x0OPv0R72obd63tMHssmGt.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Venom: Let There Be Carnage")
                .description("After finding a host body in investigative reporter Eddie Brock, the alien symbiote must face a new enemy, Carnage, the alter ego of serial killer Cletus Kasady.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("My Hero Academia: World Heroes' Mission")
                .description("A mysterious group called Humarize strongly believes in the Quirk Singularity Doomsday theory which states that when quirks get mixed further in with future generations, that power will bring forth the end of humanity. In order to save everyone, the Pro-Heroes around the world ask UA Academy heroes-in-training to assist them and form a world-classic selected hero team. It is up to the heroes to save the world and the future of heroes in what is the most dangerous crisis to take place yet in My Hero Academia.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/4NUzcKtYPKkfTwKsLjwNt8nRIXV.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Eternals")
                .description("The Eternals are a team of ancient aliens who have been living on Earth in secret for thousands of years. When an unexpected tragedy forces them out of the shadows, they are forced to reunite against mankind�s most ancient enemy, the Deviants.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/bcCBq9N1EMo3daNIjWJ8kYvrQm6.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(37L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Desperate Riders")
                .description("After Kansas Red rescues young Billy from a card-game shootout, the boy asks Red for help protecting his family from the outlaw Thorn, who�s just kidnapped Billy�s mother, Carol. As Red and Billy ride off to rescue Carol, they run into beautiful, tough-as-nails Leslie, who�s managed to escape Thorn�s men. The three race to stop Thorn�s wedding to Carol with guns a-blazing - but does she want to be rescued?")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/7pYYGm1dWZGkbJuhcuaHD6nE6k7.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(80L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("The Commando")
                .description("An elite DEA agent returns home after a failed mission when his family makes an unexpected discovery in their house � a stash of money worth $3 million. They soon face the danger and threat of a newly released criminal and his crew, who will do whatever it takes to retrieve the money, including kidnap the agent�s daughters. Stakes are high and lives are at risk in this head-to-head battle as the agent stops at nothing to protect his family against the money-hungry criminals.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/mC66fsuzlYHSoZwb6y6emSCaRv5.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("Sonic the Hedgehog")
                .description("Powered with incredible speed, Sonic The Hedgehog embraces his new home on Earth. That is, until Sonic sparks the attention of super-uncool evil genius Dr. Robotnik. Now it�s super-villain vs. super-sonic in an all-out race across the globe to stop Robotnik from using Sonic�s unique power for world domination.")
                .year(2020)
                .imageUrl("https://image.tmdb.org/t/p/w500/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(80L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Fistful of Vengeance")
                .description("A revenge mission becomes a fight to save the world from an ancient threat when superpowered assassin Kai tracks a killer to Bangkok.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/3cccEF9QZgV9bLWyupJO41HSrOV.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        map.put(28, movies); // Action

        /* Genre: Horror */
        movies = new LinkedHashSet<>();
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("No Exit")
                .description("Stranded at a rest stop in the mountains during a blizzard, a recovering addict discovers a kidnapped child hidden in a car belonging to one of the people inside the building which sets her on a terrifying struggle to identify who among them is the kidnapper.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/5cnLoWq9o5tuLe1Zq4BTX4LwZ2B.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("The Grandmother")
                .description("A Paris model must return to Madrid where her grandmother, who raised her, has had a stroke. But spending just a few days with this relative turns into an unexpected nightmare.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/eIUixNvox4U4foL5Z9KbN9HXYSM.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("Bull Shark")
                .description("A hungry shark begins feeding on unsuspecting lake goers in a small Texas town.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/wGE4ImqYjJZQi3xFu4I2OLm8m0w.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Resident Evil: Welcome to Raccoon City")
                .description("Once the booming home of pharmaceutical giant Umbrella Corporation, Raccoon City is now a dying Midwestern town. The company�s exodus left the city a wasteland�with great evil brewing below the surface. When that evil is unleashed, the townspeople are forever�changed�and a small group of survivors must work together to uncover the truth behind Umbrella and make it through the night.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/7uRbWOXxpWDMtnsd2PF3clu65jc.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("The Jack in the Box: Awakening")
                .description("When a vintage Jack-in-the-box is opened by a dying woman, she enters into a deal with the demon within that would see her illness cured in return for helping it claim six innocent victims.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/3Ib8vlWTrAKRrTWUrTrZPOMW4jp.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Scream")
                .description("Twenty-five years after a streak of brutal murders shocked the quiet town of Woodsboro, a new killer has donned the Ghostface mask and begins targeting a group of teenagers to resurrect secrets from the town�s deadly past.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/1m3W6cpgwuIyjtg5nSnPx7yFkXW.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("The House")
                .description("Across different eras, a poor family, an anxious developer and a fed-up landlady become tied to the same mysterious house in this animated dark comedy.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/iZjMFSKCrleKolC1gYcz5Rs8bk1.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Texas Chainsaw Massacre")
                .description("In this sequel, influencers looking to breathe new life into a Texas ghost town encounter Leatherface, an infamous killer who wears a mask of human skin.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/meRIRfADEGVo65xgPO6eZvJ0CRG.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("Virus:32")
                .description("A virus is unleashed and a chilling massacre runs through the streets of Montevideo.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/wZiF79hbhLK1U2Pj9bF67NAKXQR.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("Echoes of a Crime")
                .description("Juli�n Lemar, a best-selling suspense writer, goes on vacation with his family to a cabin in the woods. During a strong storm, the power goes out and a woman shows up desperately asking for help: her husband killed her son and now he wants to kill her. From that moment on, danger and deception are a constant threat and for Juli�n a hellish night begins until he discovers the truth.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/6b0cvhxUtzgOZitexarQTLTHDCU.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("Old")
                .description("A group of families on a tropical holiday discover that the secluded beach where they are staying is somehow causing them to age rapidly � reducing their entire lives into a single day.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/vclShucpUmPhdAOmKgf3B3Z4POD.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("Queen of Spades")
                .description("According to legend, an ominous entity known as the Queen of Spades can be summoned by performing an ancient ritual. Four teenagers summon the Queen of Spades, but they could never imagine the horrors that await them.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/4IKBzVBPLXu9p5cfEfdJjHdlafV.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("The Hunting")
                .description("When a mysterious animal attack leaves a mutilated body in the forest, a conservative small town detective must enlist the help of an eager wildlife specialist to uncover the dark and disturbing truth that threatens the town.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/kvhrltQIRp1u84ao9uj52YPaWNY.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("Antlers")
                .description("A small-town Oregon teacher and her brother, the local sheriff, discover a young student is harbouring a dangerous secret that could have frightening consequences.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/cMch3tiexw3FdOEeZxMWVel61Xg.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("Two")
                .description("Two people, a man and a woman, wake up naked and with their abdomens attached to each other.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/5P7QwmoYl70tsRZ8e0VnI9RI1MF.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Paranormal Activity: Next of Kin")
                .description("Margot, a documentary filmmaker, heads to a secluded Amish community in the hopes of learning about her long-lost mother and extended family. Following a string of strange occurrences and discoveries, she comes to realize this community may not be what it seems.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/bXAVveHiLotZbWdg3PKGhAzxYKP.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Meander")
                .description("After getting a car ride from an unknown man, Lisa wakes up in a tube. On her arm is strapped a bracelet with a countdown. She quickly understands that every 8 minutes, fire burns an occupied section. She has no choice but to crawl into safe sections to survive. To know why she�s there and how to get out, Lisa will have to face the memories of her dead daughter�")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/2OTIAx8AmwGCaXmq5ohQCyPUjdC.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("The Exorcism of God")
                .description("An American priest working in Mexico is considered a saint by many local parishioners. However, due to a botched exorcism, he carries a secret that�s eating him alive until he gets an opportunity to face his demon one final time.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/pcqo9D8Bdppt6t9fBliJYPROZkv.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("The Unholy")
                .description("Alice is young hearing-impaired girl who, after a supposed visitation from the Virgin Mary, is inexplicably able to hear, speak and heal the sick. As word spreads and people from near and far flock to witness her miracles, a disgraced journalist hoping to revive his career visits the small New England town to investigate. When terrifying events begin to happen all around, he starts to question if these phenomena are the works of the Virgin Mary or something much more sinister.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/cFOWiYDQ8Nttmt0K6PU38L48wWK.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("Don't Breathe 2")
                .description("The Blind Man has been hiding out for several years in an isolated cabin and has taken in and raised a young girl orphaned from a devastating house fire. Their quiet life together is shattered when a group of criminals kidnap the girl, forcing the Blind Man to leave his safe haven to save her.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/r7HEBkkRN93d3eFBZgPJfRaob5p.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        map.put(27, movies); // Horror

        /* Genre: Science Fiction */
        movies = new LinkedHashSet<>();
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Spider-Man: No Way Home")
                .description("Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("Sonic the Hedgehog 2")
                .description("After settling in Green Hills, Sonic is eager to prove he has what it takes to be a true hero. His test comes when Dr. Robotnik returns, this time with a new partner, Knuckles, in search for an emerald that has the power to destroy civilizations. Sonic teams up with his own sidekick, Tails, and together they embark on a globe-trotting journey to find the emerald before it falls into the wrong hands.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Moonfall")
                .description("A mysterious force knocks the moon from its orbit around Earth and sends it hurtling on a collision course with life as we know it.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/odVv1sqVs0KxBXiA8bhIBlPgalx.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("War of the Worlds: Annihilation")
                .description("A mother and son find themselves faced with a brutal alien invasion where survival will depend on discovering the unthinkable truth about the enemy.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/9eiUNsUAw2iwVyMeXNNiNQQad4E.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("The Adam Project")
                .description("After accidentally crash-landing in 2022, time-traveling fighter pilot Adam Reed teams up with his 12-year-old self on a mission to save the future.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/wFjboE0aFZNbVOF05fzrka9Fqyx.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(10749L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(18L));
        movies.add(MoviePayload.builder()
                .title("The In Between")
                .description("After surviving a car accident that took the life of her boyfriend, a teenage girl believes he's attempting to reconnect with her from the after world.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/7RcyjraM1cB1Uxy2W9ZWrab4KCw.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Venom: Let There Be Carnage")
                .description("After finding a host body in investigative reporter Eddie Brock, the alien symbiote must face a new enemy, Carnage, the alter ego of serial killer Cletus Kasady.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Eternals")
                .description("The Eternals are a team of ancient aliens who have been living on Earth in secret for thousands of years. When an unexpected tragedy forces them out of the shadows, they are forced to reunite against mankind�s most ancient enemy, the Deviants.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/bcCBq9N1EMo3daNIjWJ8kYvrQm6.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("Sonic the Hedgehog")
                .description("Powered with incredible speed, Sonic The Hedgehog embraces his new home on Earth. That is, until Sonic sparks the attention of super-uncool evil genius Dr. Robotnik. Now it�s super-villain vs. super-sonic in an all-out race across the globe to stop Robotnik from using Sonic�s unique power for world domination.")
                .year(2020)
                .imageUrl("https://image.tmdb.org/t/p/w500/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Morbius")
                .description("Dangerously ill with a rare blood disorder, and determined to save others suffering his same fate, Dr. Michael Morbius attempts a desperate gamble. What at first appears to be a radical success soon reveals itself to be a remedy potentially worse than the disease.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/6nhwr1LCozBiIN47b8oBEomOADm.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Resident Evil: Welcome to Raccoon City")
                .description("Once the booming home of pharmaceutical giant Umbrella Corporation, Raccoon City is now a dying Midwestern town. The company�s exodus left the city a wasteland�with great evil brewing below the surface. When that evil is unleashed, the townspeople are forever�changed�and a small group of survivors must work together to uncover the truth behind Umbrella and make it through the night.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/7uRbWOXxpWDMtnsd2PF3clu65jc.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("The Matrix Resurrections")
                .description("Plagued by strange memories, Neo's life takes an unexpected turn when he finds himself back inside the Matrix.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/8c4a8kE7PizaGQQnditMmI1xbRp.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Free Guy")
                .description("A bank teller called Guy realizes he is a background character in an open world video game called Free City that will soon go offline.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/xmbU4JTUm8rsdtn7Y3Fcm30GpeT.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Dune")
                .description("Paul Atreides, a brilliant and gifted young man born into a great destiny beyond his understanding, must travel to the most dangerous planet in the universe to ensure the future of his family and his people. As malevolent forces explode into conflict over the planet's exclusive supply of the most precious resource in existence-a commodity capable of unlocking humanity's greatest potential-only those who can conquer their fear will survive.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV94XAgMIckC.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(35L));
        movies.add(MoviePayload.builder()
                .title("Ron's Gone Wrong")
                .description("In a world where walking, talking, digitally connected bots have become children's best friends, an 11-year-old finds that his robot buddy doesn't quite work the same as the others do.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/plzgQAXIEHm4Y92ktxU6fedUc0x.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Avengers: Infinity War")
                .description("As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.")
                .year(2018)
                .imageUrl("https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Doctor Strange")
                .description("After his career is destroyed, a brilliant but arrogant surgeon gets a new lease on life when a sorcerer takes him under her wing and trains him to defend the world against evil.")
                .year(2016)
                .imageUrl("https://image.tmdb.org/t/p/w500/uGBVj3bEbCoZbDjjl9wTxcygko1.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10749L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("AI Love You")
                .description("A modern love story set in the near future where an AI building is powered by human feelings. Due to a software glitch, it falls in love with a real girl, escapes the building into the body of a real man, and tries to win her affections.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/sBiJOvHCSWORnFpc4yItflIkdTi.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Zack Snyder's Justice League")
                .description("Determined to ensure Superman's ultimate sacrifice was not in vain, Bruce Wayne aligns forces with Diana Prince with plans to recruit a team of metahumans to protect the world from an approaching threat of catastrophic proportions.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/tnAuB8q5vv7Ax9UAEje5Xi4BXik.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Mother/Android")
                .description("Georgia and her boyfriend Sam go on a treacherous journey to escape their country, which is caught in an unexpected war with artificial intelligence. Days away from the arrival of their first child, the couple must face No Man�s Land�a stronghold of the android uprising�in hopes of reaching safety before giving birth.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/rO3nV9d1wzHEWsC7xgwxotjZQpM.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        map.put(878, movies); // Science Fiction

        /* Genre: War */
        movies = new LinkedHashSet<>();
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(10752L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("The King's Man")
                .description("As a collection of history's worst tyrants and criminal masterminds gather to plot a war to wipe out millions, one man must race against time to stop them.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/aq4Pwv5Xeuvj6HZKtxyd23e6bE9.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("Zeros and Ones")
                .description("Called to Rome to stop an imminent terrorist bombing, a soldier desperately seeks news of his imprisoned brother � a rebel with knowledge that could thwart the attack. Navigating the capital's darkened streets, he races to a series of ominous encounters to keep the Vatican from being blown to bits.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/a6balsDWCFMHPaPT6rFoBpNjR6z.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(10752L));
        genresMap.add(genres.get(36L));
        genresMap.add(genres.get(18L));
        movies.add(MoviePayload.builder()
                .title("Amina")
                .description("In 16th-century Zazzau, now Zaria, Nigeria, Amina must utilize her military skills and tactics to defend her family's kingdom. Based on a true story.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/hMIQiwLpBfTfe3ZbRlNx4225Mgg.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(36L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("Hacksaw Ridge")
                .description("WWII American Army Medic Desmond T. Doss, who served during the Battle of Okinawa, refuses to kill people and becomes the first Conscientious Objector in American history to receive the Congressional Medal of Honor.")
                .year(2016)
                .imageUrl("https://image.tmdb.org/t/p/w500/fTuxNlgEm04PPFkr1xfK94Jn8BW.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(10752L));
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Fury")
                .description("In the last months of World War II, as the Allies make their final push in the European theatre, a battle-hardened U.S. Army sergeant named 'Wardaddy' commands a Sherman tank called 'Fury' and its five-man crew on a deadly mission behind enemy lines. Outnumbered and outgunned, Wardaddy and his men face overwhelming odds in their heroic attempts to strike at the heart of Nazi Germany.")
                .year(2014)
                .imageUrl("https://image.tmdb.org/t/p/w500/pfte7wdMobMF4CVHuOxyu6oqeeA.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("Brothers")
                .description("When his helicopter goes down during his fourth tour of duty in Afghanistan, Marine Sam Cahill is presumed dead. Back home, brother Tommy steps in to look over Sam�s wife, Grace, and two children. Sam�s surprise homecoming triggers domestic mayhem.")
                .year(2009)
                .imageUrl("https://image.tmdb.org/t/p/w500/ySXRgHCdelVtvUyHcKGpiQKb052.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10752L));
        genresMap.add(genres.get(18L));
        movies.add(MoviePayload.builder()
                .title("Halo Legends")
                .description("The universe of the Halo video game series is expanded in seven short animated films from Japan's greatest anime directors and studios.")
                .year(2010)
                .imageUrl("https://image.tmdb.org/t/p/w500/cO3iAp2pPNdWE9eqS4pTEY5HghY.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("Halo: Landfall")
                .description("Director Neill Blomkamp explores the lives of Marines and ODSTs on a last, desperate mission in a post-invasion Earth � a mission that may secure the salvation, or usher the destruction of the entire galaxy. Edited together as a standalone piece for the first time, these three shorts are the first glimpse at what a live-action Halo could and should look like and a must-see for Halo fans of every stripe.")
                .year(2007)
                .imageUrl("https://image.tmdb.org/t/p/w500/gNJMWR2j3MB2zYbzbON401kaDiU.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("Forgive Us Our Trespasses")
                .description("Targeted by Nazis as they hunt down and murder people with disabilities, a boy with a limb difference makes a daring decision while running for his life.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/4RYZSHM3eaxXAnjbgNiVaqmekL8.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("War for the Planet of the Apes")
                .description("Caesar and his apes are forced into a deadly conflict with an army of humans led by a ruthless Colonel. After the apes suffer unimaginable losses, Caesar wrestles with his darker instincts and begins his own mythic quest to avenge his kind. As the journey finally brings them face to face, Caesar and the Colonel are pitted against each other in an epic battle that will determine the fate of both their species and the future of the planet.")
                .year(2017)
                .imageUrl("https://image.tmdb.org/t/p/w500/fOTNGIdlOO4A5X4ahVqNhSczYV9.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("Inglourious Basterds")
                .description("In Nazi-occupied France during World War II, a group of Jewish-American soldiers known as \"The Basterds\" are chosen specifically to spread fear throughout the Third Reich by scalping and brutally killing Nazis. The Basterds, lead by Lt. Aldo Raine soon cross paths with a French-Jewish teenage girl who runs a movie theater in Paris which is targeted by the soldiers.")
                .year(2009)
                .imageUrl("https://image.tmdb.org/t/p/w500/7sfbEnaARXDDhKm0CZ7D7uc2sbo.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("Halo 4: Forward Unto Dawn")
                .description("A coming-of-age story, Forward Unto Dawn follows a cadet, Thomas Lasky, at a twenty-sixth century military training academy as it is attacked by the Covenant, an alliance of alien zealots. Lasky is unsure of his future within the military but feels pressured to follow in the footsteps of his mother and brother. Lasky and his surviving squad mates are rescued by the Master Chief and, after the death of his romantic interest, Chyler Silva, Lasky is inspired by the Chief to take initiative and aids him in saving the remaining cadets.")
                .year(2012)
                .imageUrl("https://image.tmdb.org/t/p/w500/9ErNM2H2992xR8R2BJrLzu70Qs3.jpg")
                .genres(genresMap)
                .rank(1)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(10752L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("American Sniper")
                .description("U.S. Navy SEAL Chris Kyle takes his sole mission�protect his comrades�to heart and becomes one of the most lethal snipers in American history. His pinpoint accuracy not only saves countless lives but also makes him a prime target of insurgents. Despite grave danger and his struggle to be a good husband and father to his family back in the States, Kyle serves four tours of duty in Iraq. However, when he finally returns home, he finds that he cannot leave the war behind.")
                .year(2014)
                .imageUrl("https://image.tmdb.org/t/p/w500/i1U46OwMc6vlm7OoSUKfqUH615e.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("Special Forces")
                .description("Afghanistan. War correspondent Elsa Casanova is taken hostage by the Taliban. Faced with her imminent execution, a Special Forces unit is dispatched to free her. In some of the world�s most breathtaking yet hostile landscapes, a relentless pursuit begins between her kidnappers who have no intention of letting their prey escape them and a group of soldiers who risk their lives in pursuit of their single aim � to bring her home alive. This strong, independent woman and these men of duty are thrown together and forced to confront situations of great danger that inextricably bind them � emotionally, violently and intimately.")
                .year(2011)
                .imageUrl("https://image.tmdb.org/t/p/w500/xpe9F3214mNrk8tnIqZvHSmIV1I.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10752L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Justice Society: World War II")
                .description("When the Flash finds himself dropped into the middle of World War II, he joins forces with Wonder Woman and her top-secret team known as the Justice Society of America.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/e4REOC6CZW8J6FslA4nRvdQXFXR.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("Rambo: First Blood Part II")
                .description("John Rambo is released from prison by the government for a top-secret covert mission to the last place on Earth he'd want to return - the jungles of Vietnam.")
                .year(1985)
                .imageUrl("https://image.tmdb.org/t/p/w500/pzPdwOitmTleVE3YPMfIQgLh84p.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("Shadow in the Cloud")
                .description("A WWII pilot traveling with top secret documents on a B-17 Flying Fortress encounters an evil presence on board the flight.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/t7EUMSlfUN3jUSZUJOLURAzJzZs.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(10752L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(36L));
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(18L));
        movies.add(MoviePayload.builder()
                .title("13 Hours: The Secret Soldiers of Benghazi")
                .description("An American Ambassador is killed during an attack at a U.S. compound in Libya as a security team struggles to make sense out of the chaos.")
                .year(2016)
                .imageUrl("https://image.tmdb.org/t/p/w500/4qnEeVPM8Yn5dIVC4k4yyjrUXeR.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(10752L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Saga of Tanya the Evil: The Movie")
                .description("With its armies sweeping across the continent, the Empire seems unstoppable. After securing victory over the remnants of the Republic's army, the Empire's ultimate victory is finally within reach. However, dark clouds are gathering in the East. The communist-led Russy Federation is mustering troops on its western border, preparing to enter the war. Supported by a detachment of Allied volunteer magicians�among whom is Mary Sioux, the daughter of a soldier killed by Tanya�the Federation is determined to spread the communist creed and bring the Empire to its knees. Meanwhile, Tanya and her battalion return to the imperial capital from the southern front. Upon their arrival, they are tasked with investigating troop movements on the border with the Federation. Any escalation of violence at this point may lead to new conflicts, plunging the world into a global war. Will the Empire eventually emerge victorious from its struggle, or will it crumble in the face radically different ideologies?")
                .year(2019)
                .imageUrl("https://image.tmdb.org/t/p/w500/omEd5EwOo3LJlGtiiYwEZeCqM3E.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(36L));
        genresMap.add(genres.get(10752L));
        movies.add(MoviePayload.builder()
                .title("Saving Private Ryan")
                .description("As U.S. troops storm the beaches of Normandy, three brothers lie dead on the battlefield, with a fourth trapped behind enemy lines. Ranger captain John Miller and seven men are tasked with penetrating German-held territory and bringing the boy home.")
                .year(1998)
                .imageUrl("https://image.tmdb.org/t/p/w500/1wY4psJ5NVEhCuOYROwLH2XExM2.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        map.put(10752, movies); // War

        /* Genre: Adventure */
        movies = new LinkedHashSet<>();
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Spider-Man: No Way Home")
                .description("Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Uncharted")
                .description("A young street-smart, Nathan Drake and his wisecracking partner Victor �Sully� Sullivan embark on a dangerous pursuit of �the greatest treasure never found� while also tracking clues that may lead to Nathan�s long-lost brother.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/sqLowacltbZLoCa4KYye64RvvdQ.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Moonfall")
                .description("A mysterious force knocks the moon from its orbit around Earth and sends it hurtling on a collision course with life as we know it.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/odVv1sqVs0KxBXiA8bhIBlPgalx.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("The Adam Project")
                .description("After accidentally crash-landing in 2022, time-traveling fighter pilot Adam Reed teams up with his 12-year-old self on a mission to save the future.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/wFjboE0aFZNbVOF05fzrka9Fqyx.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Pil's Adventures")
                .description("Pil, a little vagabond girl, lives on the streets of the medieval city of Roc-en-Brume, along with her three tame weasels. She survives of food stolen from the castle of the sinister Regent Tristain. One day, to escape his guards, Pil disguises herself as a princess. Thus she embarks upon a mad, delirious adventure, together with Crobar, a big clumsy guard who thinks she's a noble, and Rigolin, a young crackpot jester. Pil is going to have to save Roland, rightful heir to the throne under the curse of a spell. This adventure will turn the entire kingdom upside down, and teach Pil that nobility can be found in all of us.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/abPQVYyNfVuGoFUfGVhlNecu0QG.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Hotel Transylvania: Transformania")
                .description("When Van Helsing's mysterious invention, the \"Monsterfication Ray,\" goes haywire, Drac and his monster pals are all transformed into humans, and Johnny becomes a monster. In their new mismatched bodies, Drac and Johnny must team up and race across the globe to find a cure before it's too late, and before they drive each other crazy.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/teCy1egGQa0y8ULJvlrDHQKnxBL.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Venom: Let There Be Carnage")
                .description("After finding a host body in investigative reporter Eddie Brock, the alien symbiote must face a new enemy, Carnage, the alter ego of serial killer Cletus Kasady.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("My Hero Academia: World Heroes' Mission")
                .description("A mysterious group called Humarize strongly believes in the Quirk Singularity Doomsday theory which states that when quirks get mixed further in with future generations, that power will bring forth the end of humanity. In order to save everyone, the Pro-Heroes around the world ask UA Academy heroes-in-training to assist them and form a world-classic selected hero team. It is up to the heroes to save the world and the future of heroes in what is the most dangerous crisis to take place yet in My Hero Academia.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/4NUzcKtYPKkfTwKsLjwNt8nRIXV.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("The Ice Age Adventures of Buck Wild")
                .description("The fearless one-eyed weasel Buck teams up with mischievous possum brothers Crash & Eddie as they head off on a new adventure into Buck's home: The Dinosaur World.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/zzXFM4FKDG7l1ufrAkwQYv2xvnh.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Fantastic Beasts: The Secrets of Dumbledore")
                .description("Professor Albus Dumbledore knows the powerful, dark wizard Gellert Grindelwald is moving to seize control of the wizarding world. Unable to stop him alone, he entrusts magizoologist Newt Scamander to lead an intrepid team of wizards and witches. They soon encounter an array of old and new beasts as they clash with Grindelwald's growing legion of followers.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/8ZbybiGYe8XM4WGmGlhF0ec5R7u.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(10752L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("The King's Man")
                .description("As a collection of history's worst tyrants and criminal masterminds gather to plot a war to wipe out millions, one man must race against time to stop them.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/aq4Pwv5Xeuvj6HZKtxyd23e6bE9.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Ghostbusters: Afterlife")
                .description("When a single mom and her two kids arrive in a small town, they begin to discover their connection to the original Ghostbusters and the secret legacy their grandfather left behind.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/sg4xJaufDiQl7caFEskBtQXfD4x.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Pirates of the Caribbean: Dead Man's Chest")
                .description("Captain Jack Sparrow works his way out of a blood debt with the ghostly Davy Jones to avoid eternal damnation.")
                .year(2006)
                .imageUrl("https://image.tmdb.org/t/p/w500/uXEqmloGyP7UXAiphJUu2v2pcuE.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Pirates of the Caribbean: On Stranger Tides")
                .description("Captain Jack Sparrow crosses paths with a woman from his past, and he's not sure if it's love -- or if she's a ruthless con artist who's using him to find the fabled Fountain of Youth. When she forces him aboard the Queen Anne's Revenge, the ship of the formidable pirate Blackbeard, Jack finds himself on an unexpected adventure in which he doesn't know who to fear more: Blackbeard or the woman from his past.")
                .year(2011)
                .imageUrl("https://image.tmdb.org/t/p/w500/keGfSvCmYj7CvdRx36OdVrAEibE.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Shang-Chi and the Legend of the Ten Rings")
                .description("Shang-Chi must confront the past he thought he left behind when he is drawn into the web of the mysterious Ten Rings organization.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/1BIoJGKbXjdFDAqUEiA2VHqkK1Z.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Pirates of the Caribbean: The Curse of the Black Pearl")
                .description("Jack Sparrow, a freewheeling 18th-century pirate, quarrels with a rival pirate bent on pillaging Port Royal. When the governor's daughter is kidnapped, Sparrow decides to help the girl's love save her.")
                .year(2003)
                .imageUrl("https://image.tmdb.org/t/p/w500/z8onk7LV9Mmw6zKz4hT6pzzvmvl.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("The Matrix Resurrections")
                .description("Plagued by strange memories, Neo's life takes an unexpected turn when he finds himself back inside the Matrix.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/8c4a8kE7PizaGQQnditMmI1xbRp.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Pirates of the Caribbean: At World's End")
                .description("Captain Barbossa, long believed to be dead, has come back to life and is headed to the edge of the Earth with Will Turner and Elizabeth Swann. But nothing is quite as it seems.")
                .year(2007)
                .imageUrl("https://image.tmdb.org/t/p/w500/2YMnBRh8F6fDGCCEIPk9Hb0cEyB.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        map.put(12, movies); // Adventure

        /* Genre: Animation */
        movies = new LinkedHashSet<>();
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Turning Red")
                .description("Thirteen-year-old Mei is experiencing the awkwardness of being a teenager with a twist � when she gets too excited, she transforms into a giant red panda.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/qsdjk9oAKSQMWs0Vt5Pyfh6O4GZ.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Encanto")
                .description("The tale of an extraordinary family, the Madrigals, who live hidden in the mountains of Colombia, in a magical house, in a vibrant town, in a wondrous, charmed place called an Encanto. The magic of the Encanto has blessed every child in the family with a unique gift from super strength to the power to heal�every child except one, Mirabel. But when she discovers that the magic surrounding the Encanto is in danger, Mirabel decides that she, the only ordinary Madrigal, might just be her exceptional family's last hope.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/4j0PNHkMr5ax3IA8tjtxcmPU3QT.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Pil's Adventures")
                .description("Pil, a little vagabond girl, lives on the streets of the medieval city of Roc-en-Brume, along with her three tame weasels. She survives of food stolen from the castle of the sinister Regent Tristain. One day, to escape his guards, Pil disguises herself as a princess. Thus she embarks upon a mad, delirious adventure, together with Crobar, a big clumsy guard who thinks she's a noble, and Rigolin, a young crackpot jester. Pil is going to have to save Roland, rightful heir to the throne under the curse of a spell. This adventure will turn the entire kingdom upside down, and teach Pil that nobility can be found in all of us.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/abPQVYyNfVuGoFUfGVhlNecu0QG.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Hotel Transylvania: Transformania")
                .description("When Van Helsing's mysterious invention, the \"Monsterfication Ray,\" goes haywire, Drac and his monster pals are all transformed into humans, and Johnny becomes a monster. In their new mismatched bodies, Drac and Johnny must team up and race across the globe to find a cure before it's too late, and before they drive each other crazy.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/teCy1egGQa0y8ULJvlrDHQKnxBL.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("My Hero Academia: World Heroes' Mission")
                .description("A mysterious group called Humarize strongly believes in the Quirk Singularity Doomsday theory which states that when quirks get mixed further in with future generations, that power will bring forth the end of humanity. In order to save everyone, the Pro-Heroes around the world ask UA Academy heroes-in-training to assist them and form a world-classic selected hero team. It is up to the heroes to save the world and the future of heroes in what is the most dangerous crisis to take place yet in My Hero Academia.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/4NUzcKtYPKkfTwKsLjwNt8nRIXV.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("The Ice Age Adventures of Buck Wild")
                .description("The fearless one-eyed weasel Buck teams up with mischievous possum brothers Crash & Eddie as they head off on a new adventure into Buck's home: The Dinosaur World.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/zzXFM4FKDG7l1ufrAkwQYv2xvnh.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(10402L));
        movies.add(MoviePayload.builder()
                .title("Sing 2")
                .description("Buster and his new cast now have their sights set on debuting a new show at the Crystal Tower Theater in glamorous Redshore City. But with no connections, he and his singers must sneak into the Crystal Entertainment offices, run by the ruthless wolf mogul Jimmy Crystal, where the gang pitches the ridiculous idea of casting the lion rock legend Clay Calloway in their show. Buster must embark on a quest to find the now-isolated Clay and persuade him to return to the stage.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/aWeKITRFbbwY8txG5uCj4rMCfSP.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("The Boss Baby: Family Business")
                .description("The Templeton brothers � Tim and his Boss Baby little bro Ted � have become adults and drifted away from each other. But a new boss baby with a cutting-edge approach and a can-do attitude is about to bring them together again � and inspire a new family business.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/kv2Qk9MKFFQo4WQPaYta599HkJP.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(35L));
        movies.add(MoviePayload.builder()
                .title("PAW Patrol: The Movie")
                .description("Ryder and the pups are called to Adventure City to stop Mayor Humdinger from turning the bustling metropolis into a state of chaos.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/ic0intvXZSfBlYPIvWXpU1ivUCO.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("The Seven Deadly Sins: Cursed by Light")
                .description("With the help of the \"Dragon Sin of Wrath\" Meliodas and the worst rebels in history, the Seven Deadly Sins, the \"Holy War\", in which four races, including Humans, Goddesses, Fairies and Giants fought against the Demons, is finally over. At the cost of the \"Lion Sin of Pride\" Escanor's life, the Demon King was defeated and the world regained peace. After that, each of the Sins take their own path.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/k0ThmZQl5nHe4JefC2bXjqtgYp0.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("Tom and Jerry: Cowboy Up!")
                .description("This time, the rivals team up to help a cowgirl and her brother save their homestead from a greedy land-grabber, and they�re going to need some help! Jerry�s three precocious nephews are all ready for action, and Tom is rounding up a posse of prairie dogs. But can a ragtag band of varmints defeat a deceitful desperado determined to deceive a damsel in distress? No matter what happens with Tom and Jerry in the saddle, it�ll be a rootin� tootin� good time!")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/muIaHotSaSUQr0KZCIJOYQEe7y2.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(80L));
        movies.add(MoviePayload.builder()
                .title("The Bad Guys")
                .description("When the infamous Bad Guys are finally caught after years of countless heists and being the world�s most-wanted villains, Mr. Wolf brokers a deal to save them all from prison.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/7qop80YfuO0BwJa1uXk1DXUUEwv.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("The House")
                .description("Across different eras, a poor family, an anxious developer and a fed-up landlady become tied to the same mysterious house in this animated dark comedy.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/iZjMFSKCrleKolC1gYcz5Rs8bk1.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Luca")
                .description("Luca and his best friend Alberto experience an unforgettable summer on the Italian Riviera. But all the fun is threatened by a deeply-held secret: they are sea monsters from another world just below the water�s surface.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/jTswp6KyDYKtvC52GbHagrZbGvD.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("The Simpsons in Plusaversary")
                .description("The Simpsons host a Disney+ Day party and everyone is on the list� except Homer. With friends from across the service and music fit for a Disney Princess, Plusaversary is Springfield's event of the year.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/p5jzbffrXuBTjsiwrQ3aOMTrvCj.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Fate/Grand Order Final Singularity � Grand Temple of Time: Solomon")
                .description("The Chaldea Organization must deal with the Grand Temple of Solomon, the King of Magic, which has come to destroy the world.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/1kRgPGHmqSpKdx1dzCjzG6QoYHb.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(35L));
        movies.add(MoviePayload.builder()
                .title("Ron's Gone Wrong")
                .description("In a world where walking, talking, digitally connected bots have become children's best friends, an 11-year-old finds that his robot buddy doesn't quite work the same as the others do.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/plzgQAXIEHm4Y92ktxU6fedUc0x.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("Jujutsu Kaisen 0")
                .description("Yuta Okkotsu is a nervous high school student who is suffering from a serious problem�his childhood friend Rika has turned into a curse and won't leave him alone. Since Rika is no ordinary curse, his plight is noticed by Satoru Gojo, a teacher at Jujutsu High, a school where fledgling exorcists learn how to combat curses. Gojo convinces Yuta to enroll, but can he learn enough in time to confront the curse that haunts him?")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/3pTwMUEavTzVOh6yLN0aEwR7uSy.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("Diary of a Wimpy Kid")
                .description("Greg Heffley is a scrawny but ambitious kid with an active imagination and big plans to be rich and famous � he just has to survive middle school first.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/obg6lWuNaZkoSlwrVG4VVk4SmT.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        map.put(16, movies); // Animation

        /* Genre: Fantasy */
        movies = new LinkedHashSet<>();
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Turning Red")
                .description("Thirteen-year-old Mei is experiencing the awkwardness of being a teenager with a twist � when she gets too excited, she transforms into a giant red panda.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/qsdjk9oAKSQMWs0Vt5Pyfh6O4GZ.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Encanto")
                .description("The tale of an extraordinary family, the Madrigals, who live hidden in the mountains of Colombia, in a magical house, in a vibrant town, in a wondrous, charmed place called an Encanto. The magic of the Encanto has blessed every child in the family with a unique gift from super strength to the power to heal�every child except one, Mirabel. But when she discovers that the magic surrounding the Encanto is in danger, Mirabel decides that she, the only ordinary Madrigal, might just be her exceptional family's last hope.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/4j0PNHkMr5ax3IA8tjtxcmPU3QT.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Pil's Adventures")
                .description("Pil, a little vagabond girl, lives on the streets of the medieval city of Roc-en-Brume, along with her three tame weasels. She survives of food stolen from the castle of the sinister Regent Tristain. One day, to escape his guards, Pil disguises herself as a princess. Thus she embarks upon a mad, delirious adventure, together with Crobar, a big clumsy guard who thinks she's a noble, and Rigolin, a young crackpot jester. Pil is going to have to save Roland, rightful heir to the throne under the curse of a spell. This adventure will turn the entire kingdom upside down, and teach Pil that nobility can be found in all of us.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/abPQVYyNfVuGoFUfGVhlNecu0QG.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Hotel Transylvania: Transformania")
                .description("When Van Helsing's mysterious invention, the \"Monsterfication Ray,\" goes haywire, Drac and his monster pals are all transformed into humans, and Johnny becomes a monster. In their new mismatched bodies, Drac and Johnny must team up and race across the globe to find a cure before it's too late, and before they drive each other crazy.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/teCy1egGQa0y8ULJvlrDHQKnxBL.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("My Hero Academia: World Heroes' Mission")
                .description("A mysterious group called Humarize strongly believes in the Quirk Singularity Doomsday theory which states that when quirks get mixed further in with future generations, that power will bring forth the end of humanity. In order to save everyone, the Pro-Heroes around the world ask UA Academy heroes-in-training to assist them and form a world-classic selected hero team. It is up to the heroes to save the world and the future of heroes in what is the most dangerous crisis to take place yet in My Hero Academia.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/4NUzcKtYPKkfTwKsLjwNt8nRIXV.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Fantastic Beasts: The Secrets of Dumbledore")
                .description("Professor Albus Dumbledore knows the powerful, dark wizard Gellert Grindelwald is moving to seize control of the wizarding world. Unable to stop him alone, he entrusts magizoologist Newt Scamander to lead an intrepid team of wizards and witches. They soon encounter an array of old and new beasts as they clash with Grindelwald's growing legion of followers.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/8ZbybiGYe8XM4WGmGlhF0ec5R7u.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(80L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Fistful of Vengeance")
                .description("A revenge mission becomes a fight to save the world from an ancient threat when superpowered assassin Kai tracks a killer to Bangkok.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/3cccEF9QZgV9bLWyupJO41HSrOV.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Ghostbusters: Afterlife")
                .description("When a single mom and her two kids arrive in a small town, they begin to discover their connection to the original Ghostbusters and the secret legacy their grandfather left behind.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/sg4xJaufDiQl7caFEskBtQXfD4x.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Pirates of the Caribbean: Dead Man's Chest")
                .description("Captain Jack Sparrow works his way out of a blood debt with the ghostly Davy Jones to avoid eternal damnation.")
                .year(2006)
                .imageUrl("https://image.tmdb.org/t/p/w500/uXEqmloGyP7UXAiphJUu2v2pcuE.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Morbius")
                .description("Dangerously ill with a rare blood disorder, and determined to save others suffering his same fate, Dr. Michael Morbius attempts a desperate gamble. What at first appears to be a radical success soon reveals itself to be a remedy potentially worse than the disease.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/6nhwr1LCozBiIN47b8oBEomOADm.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Pirates of the Caribbean: On Stranger Tides")
                .description("Captain Jack Sparrow crosses paths with a woman from his past, and he's not sure if it's love -- or if she's a ruthless con artist who's using him to find the fabled Fountain of Youth. When she forces him aboard the Queen Anne's Revenge, the ship of the formidable pirate Blackbeard, Jack finds himself on an unexpected adventure in which he doesn't know who to fear more: Blackbeard or the woman from his past.")
                .year(2011)
                .imageUrl("https://image.tmdb.org/t/p/w500/keGfSvCmYj7CvdRx36OdVrAEibE.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Shang-Chi and the Legend of the Ten Rings")
                .description("Shang-Chi must confront the past he thought he left behind when he is drawn into the web of the mysterious Ten Rings organization.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/1BIoJGKbXjdFDAqUEiA2VHqkK1Z.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Pirates of the Caribbean: The Curse of the Black Pearl")
                .description("Jack Sparrow, a freewheeling 18th-century pirate, quarrels with a rival pirate bent on pillaging Port Royal. When the governor's daughter is kidnapped, Sparrow decides to help the girl's love save her.")
                .year(2003)
                .imageUrl("https://image.tmdb.org/t/p/w500/z8onk7LV9Mmw6zKz4hT6pzzvmvl.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(18L));
        movies.add(MoviePayload.builder()
                .title("The Lovely Bones")
                .description("After being brutally murdered, 14-year-old Susie Salmon watches from heaven over her grief-stricken family -- and her killer. As she observes their daily lives, she must balance her thirst for revenge with her desire for her family to heal.")
                .year(2009)
                .imageUrl("https://image.tmdb.org/t/p/w500/qPQpTz2ZdsznXTkNc8Kk8slEVZF.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Pirates of the Caribbean: At World's End")
                .description("Captain Barbossa, long believed to be dead, has come back to life and is headed to the edge of the Earth with Will Turner and Elizabeth Swann. But nothing is quite as it seems.")
                .year(2007)
                .imageUrl("https://image.tmdb.org/t/p/w500/2YMnBRh8F6fDGCCEIPk9Hb0cEyB.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("The Seven Deadly Sins: Cursed by Light")
                .description("With the help of the \"Dragon Sin of Wrath\" Meliodas and the worst rebels in history, the Seven Deadly Sins, the \"Holy War\", in which four races, including Humans, Goddesses, Fairies and Giants fought against the Demons, is finally over. At the cost of the \"Lion Sin of Pride\" Escanor's life, the Demon King was defeated and the world regained peace. After that, each of the Sins take their own path.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/k0ThmZQl5nHe4JefC2bXjqtgYp0.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Doctor Strange in the Multiverse of Madness")
                .description("Doctor Strange, with the help of mystical allies both old and new, traverses the mind-bending and dangerous alternate realities of the Multiverse to confront a mysterious new adversary.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/nLA4VD1Q6ZvpgCxIMwoHVwW6yaC.jpg")
                .genres(genresMap)
                .rank(0)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Clifford the Big Red Dog")
                .description("As Emily struggles to fit in at home and at school, she discovers a small red puppy who is destined to become her best friend. When Clifford magically undergoes one heck of a growth spurt, becomes a gigantic dog and attracts the attention of a genetics company, Emily and her Uncle Casey have to fight the forces of greed as they go on the run across New York City. Along the way, Clifford affects the lives of everyone around him and teaches Emily and her uncle the true meaning of acceptance and unconditional love.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/oifhfVhUcuDjE61V5bS5dfShQrm.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        map.put(14, movies); // Fantasy

        /* Genre: Family */
        movies = new LinkedHashSet<>();
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Turning Red")
                .description("Thirteen-year-old Mei is experiencing the awkwardness of being a teenager with a twist � when she gets too excited, she transforms into a giant red panda.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/qsdjk9oAKSQMWs0Vt5Pyfh6O4GZ.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("Sonic the Hedgehog 2")
                .description("After settling in Green Hills, Sonic is eager to prove he has what it takes to be a true hero. His test comes when Dr. Robotnik returns, this time with a new partner, Knuckles, in search for an emerald that has the power to destroy civilizations. Sonic teams up with his own sidekick, Tails, and together they embark on a globe-trotting journey to find the emerald before it falls into the wrong hands.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Encanto")
                .description("The tale of an extraordinary family, the Madrigals, who live hidden in the mountains of Colombia, in a magical house, in a vibrant town, in a wondrous, charmed place called an Encanto. The magic of the Encanto has blessed every child in the family with a unique gift from super strength to the power to heal�every child except one, Mirabel. But when she discovers that the magic surrounding the Encanto is in danger, Mirabel decides that she, the only ordinary Madrigal, might just be her exceptional family's last hope.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/4j0PNHkMr5ax3IA8tjtxcmPU3QT.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Pil's Adventures")
                .description("Pil, a little vagabond girl, lives on the streets of the medieval city of Roc-en-Brume, along with her three tame weasels. She survives of food stolen from the castle of the sinister Regent Tristain. One day, to escape his guards, Pil disguises herself as a princess. Thus she embarks upon a mad, delirious adventure, together with Crobar, a big clumsy guard who thinks she's a noble, and Rigolin, a young crackpot jester. Pil is going to have to save Roland, rightful heir to the throne under the curse of a spell. This adventure will turn the entire kingdom upside down, and teach Pil that nobility can be found in all of us.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/abPQVYyNfVuGoFUfGVhlNecu0QG.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Hotel Transylvania: Transformania")
                .description("When Van Helsing's mysterious invention, the \"Monsterfication Ray,\" goes haywire, Drac and his monster pals are all transformed into humans, and Johnny becomes a monster. In their new mismatched bodies, Drac and Johnny must team up and race across the globe to find a cure before it's too late, and before they drive each other crazy.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/teCy1egGQa0y8ULJvlrDHQKnxBL.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("The Ice Age Adventures of Buck Wild")
                .description("The fearless one-eyed weasel Buck teams up with mischievous possum brothers Crash & Eddie as they head off on a new adventure into Buck's home: The Dinosaur World.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/zzXFM4FKDG7l1ufrAkwQYv2xvnh.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(10402L));
        movies.add(MoviePayload.builder()
                .title("Sing 2")
                .description("Buster and his new cast now have their sights set on debuting a new show at the Crystal Tower Theater in glamorous Redshore City. But with no connections, he and his singers must sneak into the Crystal Entertainment offices, run by the ruthless wolf mogul Jimmy Crystal, where the gang pitches the ridiculous idea of casting the lion rock legend Clay Calloway in their show. Buster must embark on a quest to find the now-isolated Clay and persuade him to return to the stage.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/aWeKITRFbbwY8txG5uCj4rMCfSP.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("Sonic the Hedgehog")
                .description("Powered with incredible speed, Sonic The Hedgehog embraces his new home on Earth. That is, until Sonic sparks the attention of super-uncool evil genius Dr. Robotnik. Now it�s super-villain vs. super-sonic in an all-out race across the globe to stop Robotnik from using Sonic�s unique power for world domination.")
                .year(2020)
                .imageUrl("https://image.tmdb.org/t/p/w500/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("The Boss Baby: Family Business")
                .description("The Templeton brothers � Tim and his Boss Baby little bro Ted � have become adults and drifted away from each other. But a new boss baby with a cutting-edge approach and a can-do attitude is about to bring them together again � and inspire a new family business.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/kv2Qk9MKFFQo4WQPaYta599HkJP.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(35L));
        movies.add(MoviePayload.builder()
                .title("PAW Patrol: The Movie")
                .description("Ryder and the pups are called to Adventure City to stop Mayor Humdinger from turning the bustling metropolis into a state of chaos.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/ic0intvXZSfBlYPIvWXpU1ivUCO.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("Tom and Jerry: Cowboy Up!")
                .description("This time, the rivals team up to help a cowgirl and her brother save their homestead from a greedy land-grabber, and they�re going to need some help! Jerry�s three precocious nephews are all ready for action, and Tom is rounding up a posse of prairie dogs. But can a ragtag band of varmints defeat a deceitful desperado determined to deceive a damsel in distress? No matter what happens with Tom and Jerry in the saddle, it�ll be a rootin� tootin� good time!")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/muIaHotSaSUQr0KZCIJOYQEe7y2.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(80L));
        movies.add(MoviePayload.builder()
                .title("The Bad Guys")
                .description("When the infamous Bad Guys are finally caught after years of countless heists and being the world�s most-wanted villains, Mr. Wolf brokers a deal to save them all from prison.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/7qop80YfuO0BwJa1uXk1DXUUEwv.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Clifford the Big Red Dog")
                .description("As Emily struggles to fit in at home and at school, she discovers a small red puppy who is destined to become her best friend. When Clifford magically undergoes one heck of a growth spurt, becomes a gigantic dog and attracts the attention of a genetics company, Emily and her Uncle Casey have to fight the forces of greed as they go on the run across New York City. Along the way, Clifford affects the lives of everyone around him and teaches Emily and her uncle the true meaning of acceptance and unconditional love.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/oifhfVhUcuDjE61V5bS5dfShQrm.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Luca")
                .description("Luca and his best friend Alberto experience an unforgettable summer on the Italian Riviera. But all the fun is threatened by a deeply-held secret: they are sea monsters from another world just below the water�s surface.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/jTswp6KyDYKtvC52GbHagrZbGvD.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(35L));
        movies.add(MoviePayload.builder()
                .title("Ron's Gone Wrong")
                .description("In a world where walking, talking, digitally connected bots have become children's best friends, an 11-year-old finds that his robot buddy doesn't quite work the same as the others do.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/plzgQAXIEHm4Y92ktxU6fedUc0x.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(10402L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(35L));
        movies.add(MoviePayload.builder()
                .title("Better Nate Than Ever")
                .description("13-year-old Nate Foster has big Broadway dreams but there�s only one problem � he can�t even land a part in the school play. When his parents leave town, Nate and his best friend Libby sneak off to the Big Apple for a once-in-a-lifetime opportunity to prove everyone wrong. A chance encounter with Nate�s long-lost Aunt Heidi turns his journey upside-down, and together they must learn that life�s greatest adventures are only as big as your dreams.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/55gkFbie2Nbudo21rDpi6zG0XvC.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("Diary of a Wimpy Kid")
                .description("Greg Heffley is a scrawny but ambitious kid with an active imagination and big plans to be rich and famous � he just has to survive middle school first.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/obg6lWuNaZkoSlwrVG4VVk4SmT.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(878L));
        movies.add(MoviePayload.builder()
                .title("Space Jam: A New Legacy")
                .description("When LeBron and his young son Dom are trapped in a digital space by a rogue A.I., LeBron must get them home safe by leading Bugs, Lola Bunny and the whole gang of notoriously undisciplined Looney Tunes to victory over the A.I.'s digitized champions on the court. It's Tunes versus Goons in the highest-stakes challenge of his life.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(35L));
        movies.add(MoviePayload.builder()
                .title("Cheaper by the Dozen")
                .description("This remake of the beloved classic follows the raucous exploits of a blended family of 12, the Bakers, as they navigate a hectic home life while simultaneously managing their family business.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/qNRsouZh5zmhaE3n4QpLDXzy1gQ.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(35L));
        movies.add(MoviePayload.builder()
                .title("Back to the Outback")
                .description("Tired of being locked in a reptile house where humans gawk at them like they are monsters, a ragtag group of Australia�s deadliest creatures plot an escape from their zoo to the Outback, a place where they�ll fit in without being judged.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/zNXNRLH5wJprUG6B1olaBTNZOjy.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        map.put(10751, movies); // Family

        /* Genre: Western */
        movies = new LinkedHashSet<>();
        genresMap = new HashSet<>();
        genresMap.add(genres.get(37L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Desperate Riders")
                .description("After Kansas Red rescues young Billy from a card-game shootout, the boy asks Red for help protecting his family from the outlaw Thorn, who�s just kidnapped Billy�s mother, Carol. As Red and Billy ride off to rescue Carol, they run into beautiful, tough-as-nails Leslie, who�s managed to escape Thorn�s men. The three race to stop Thorn�s wedding to Carol with guns a-blazing - but does she want to be rescued?")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/7pYYGm1dWZGkbJuhcuaHD6nE6k7.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("Tom and Jerry: Cowboy Up!")
                .description("This time, the rivals team up to help a cowgirl and her brother save their homestead from a greedy land-grabber, and they�re going to need some help! Jerry�s three precocious nephews are all ready for action, and Tom is rounding up a posse of prairie dogs. But can a ragtag band of varmints defeat a deceitful desperado determined to deceive a damsel in distress? No matter what happens with Tom and Jerry in the saddle, it�ll be a rootin� tootin� good time!")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/muIaHotSaSUQr0KZCIJOYQEe7y2.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("The Harder They Fall")
                .description("Gunning for revenge, outlaw Nat Love saddles up with his gang to take down enemy Rufus Buck, a ruthless crime boss who just got sprung from prison.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/su9WzL7lwUZPhjH6eZByAYFx2US.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("Spirit Untamed")
                .description("Lucky Prescott's life is changed forever when she moves from her home in the city to a small frontier town and befriends a wild mustang named Spirit.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/q4WaFHk9Vp1poc88X1szwFRtYc5.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(37L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Last Shoot Out")
                .description("Soon after a newlywed learns that her husband had her father shot down, she flees from the Callahan ranch in fear. She's rescued by a gunman who safeguards her at a remote outpost as he staves off her husband's attempts to reclaim his bride.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/pvEtPxotI3POlVPvNxgrHJuDXfe.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(37L));
        genresMap.add(genres.get(28L));
        movies.add(MoviePayload.builder()
                .title("Catch the Bullet")
                .description("U.S. marshal Britt MacMasters returns from a mission to find his father wounded and his son kidnapped by the outlaw Jed Blake. Hot on their trail, Britt forms a posse with a gunslinging deputy and a stoic Pawnee tracker. But Jed and Britt tread dangerously close to the Red Desert�s Sioux territory.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/7PoomidF9HlMKXcAyOJ87lGkhSp.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(37L));
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(18L));
        movies.add(MoviePayload.builder()
                .title("Spirit: Stallion of the Cimarron")
                .description("As a wild stallion travels across the frontiers of the Old West, he befriends a young human and finds true love with a mare.")
                .year(2002)
                .imageUrl("https://image.tmdb.org/t/p/w500/rXhd4p6tHlgYHqRtn1wdrA2EZwQ.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(37L));
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Jonah Hex")
                .description("Gunslinger Jonah Hex is appointed by President Ulysses Grant to track down terrorist Quentin Turnbull, a former Confederate officer determined on unleashing hell on earth. Jonah not only secures freedom by accepting this task, he also gets revenge on the man who slayed his wife and child.")
                .year(2010)
                .imageUrl("https://image.tmdb.org/t/p/w500/1MpWjcCn8M0763QDoxcN0gXrc5q.jpg")
                .genres(genresMap)
                .rank(4)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("The Power of the Dog")
                .description("A domineering but charismatic rancher wages a war of intimidation on his brother's new wife and her teen son, until long-hidden secrets come to light.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/kEy48iCzGnp0ao1cZbNeWR6yIhC.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("The Lone Ranger")
                .description("The Texas Rangers chase down a gang of outlaws led by Butch Cavendish, but the gang ambushes the Rangers, seemingly killing them all. One survivor is found, however, by an American Indian named Tonto, who nurses him back to health. The Ranger, donning a mask and riding a white stallion named Silver, teams up with Tonto to bring the unscrupulous gang and others of that ilk to justice.")
                .year(2013)
                .imageUrl("https://image.tmdb.org/t/p/w500/p3OvQFa5lhbwSAhPygwnlugie1d.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(37L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("Rango")
                .description("When Rango, a lost family pet, accidentally winds up in the gritty, gun-slinging town of Dirt, the less-than-courageous lizard suddenly finds he stands out. Welcomed as the last hope the town has been waiting for, new Sheriff Rango is forced to play his new role to the hilt.")
                .year(2011)
                .imageUrl("https://image.tmdb.org/t/p/w500/h9GJiKuuJ650dte0gyzLD3ILcIh.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(37L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("The Warrior's Way")
                .description("A warrior-assassin is forced to hide in a small town in the American Badlands after refusing a mission.")
                .year(2010)
                .imageUrl("https://image.tmdb.org/t/p/w500/kfloVZk0uGkSftjD6DI8ZWUsrrI.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("Cowboys & Aliens")
                .description("A stranger stumbles into the desert town of Absolution with no memory of his past and a futuristic shackle around his wrist. With the help of mysterious beauty Ella and the iron-fisted Colonel Dolarhyde, he finds himself leading an unlikely posse of cowboys, outlaws, and Apache warriors against a common enemy from beyond this world in an epic showdown for survival.")
                .year(2011)
                .imageUrl("https://image.tmdb.org/t/p/w500/9uZsGCP4rvOHVGCpMpYq5gNCuNI.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("Django Unchained")
                .description("With the help of a German bounty hunter, a freed slave sets out to rescue his wife from a brutal Mississippi plantation owner.")
                .year(2012)
                .imageUrl("https://image.tmdb.org/t/p/w500/7oWY8VDWW7thTzWh3OKYRkWUlD5.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("The Magnificent Seven")
                .description("Looking to mine for gold, greedy industrialist Bartholomew Bogue seizes control of the Old West town of Rose Creek. With their lives in jeopardy, Emma Cullen and other desperate residents turn to bounty hunter Sam Chisolm for help. Chisolm recruits an eclectic group of gunslingers to take on Bogue and his ruthless henchmen. With a deadly showdown on the horizon, the seven mercenaries soon find themselves fighting for more than just money once the bullets start to fly.")
                .year(2016)
                .imageUrl("https://image.tmdb.org/t/p/w500/ezcS78TIjgr85pVdaPDd2rSPVNs.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("The Good, the Bad and the Ugly")
                .description("While the Civil War rages between the Union and the Confederacy, three men � a quiet loner, a ruthless hit man and a Mexican bandit � comb the American Southwest in search of a strongbox containing $200,000 in stolen gold.")
                .year(1966)
                .imageUrl("https://image.tmdb.org/t/p/w500/bX2xnavhMYjWDoZp1VM6VnU1xwe.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(37L));
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(12L));
        movies.add(MoviePayload.builder()
                .title("The Revenant")
                .description("In the 1820s, a frontiersman, Hugh Glass, sets out on a path of vengeance against those who left him for dead after a bear mauling.")
                .year(2015)
                .imageUrl("https://image.tmdb.org/t/p/w500/tSaBkriE7TpbjFoQUFXuikoz0dF.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(37L));
        genresMap.add(genres.get(35L));
        movies.add(MoviePayload.builder()
                .title("The Ridiculous 6")
                .description("When his long-lost outlaw father returns, Tommy \"White Knife\" Stockburn goes on an adventure-filled journey across the Old West with his five brothers.")
                .year(2015)
                .imageUrl("https://image.tmdb.org/t/p/w500/kPc80jywR5Nm2KIQjkY5i4VXSx4.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(80L));
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("Lawless")
                .description("Set in the Depression-era Franklin County, Virginia, a bootlegging gang is threatened by authorities who want a cut of their profits.")
                .year(2012)
                .imageUrl("https://image.tmdb.org/t/p/w500/Ahtzwts22ayviD3LEVslfL4nRWB.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(37L));
        movies.add(MoviePayload.builder()
                .title("An American Tail: Fievel Goes West")
                .description("Some time after the Mousekewitz's have settled in America, they find that they are still having problems with the threat of cats. That makes them eager to try another home out in the west, where they are promised that mice and cats live in peace. Unfortunately, the one making this claim is an oily con artist named Cat R. Waul who is intent on his own sinister plan.")
                .year(1991)
                .imageUrl("https://image.tmdb.org/t/p/w500/cImuS7FE9MHT5vnwIHGx1Ryh0K1.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        map.put(37, movies); // Western

        /* Genre: Mystery */
        movies = new LinkedHashSet<>();
        genresMap = new HashSet<>();
        genresMap.add(genres.get(80L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("The Batman")
                .description("In his second year of fighting crime, Batman uncovers corruption in Gotham City that connects to his own family while facing a serial killer known as the Riddler.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/74xTEgt7R36Fpooo50r9T25onhq.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(10752L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("The King's Man")
                .description("As a collection of history's worst tyrants and criminal masterminds gather to plot a war to wipe out millions, one man must race against time to stop them.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/aq4Pwv5Xeuvj6HZKtxyd23e6bE9.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Scream")
                .description("Twenty-five years after a streak of brutal murders shocked the quiet town of Woodsboro, a new killer has donned the Ghostface mask and begins targeting a group of teenagers to resurrect secrets from the town�s deadly past.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/1m3W6cpgwuIyjtg5nSnPx7yFkXW.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(80L));
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Death on the Nile")
                .description("Belgian sleuth Hercule Poirot boards a glamorous river steamer with enough champagne to fill the Nile. But his Egyptian vacation turns into a thrilling search for a murderer when a picture-perfect couple�s idyllic honeymoon is tragically cut short.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/kVr5zIAFSPRQ57Y1zE7KzmhzdMQ.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Deep Water")
                .description("Vic and Melinda Van Allen are a couple in the small town of Little Wesley. Their loveless marriage is held together only by a precarious arrangement whereby, in order to avoid the messiness of divorce, Melinda is allowed to take any number of lovers as long as she does not desert her family.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/6yRMyWwjuhKg6IU66uiZIGhaSc8.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(18L));
        movies.add(MoviePayload.builder()
                .title("Brazen")
                .description("Mystery writer Grace Miller has killer instincts when it comes to motive - and she'll need every bit of expertise to help solve her sister's murder.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/7e4n1GfC9iky9VQzH3cDQz9wYpO.jpg")
                .genres(genresMap)
                .rank(4)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(27L));
        movies.add(MoviePayload.builder()
                .title("Old")
                .description("A group of families on a tropical holiday discover that the secluded beach where they are staying is somehow causing them to age rapidly � reducing their entire lives into a single day.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/vclShucpUmPhdAOmKgf3B3Z4POD.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("Jujutsu Kaisen 0")
                .description("Yuta Okkotsu is a nervous high school student who is suffering from a serious problem�his childhood friend Rika has turned into a curse and won't leave him alone. Since Rika is no ordinary curse, his plight is noticed by Satoru Gojo, a teacher at Jujutsu High, a school where fledgling exorcists learn how to combat curses. Gojo convinces Yuta to enroll, but can he learn enough in time to confront the curse that haunts him?")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/3pTwMUEavTzVOh6yLN0aEwR7uSy.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("Antlers")
                .description("A small-town Oregon teacher and her brother, the local sheriff, discover a young student is harbouring a dangerous secret that could have frightening consequences.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/cMch3tiexw3FdOEeZxMWVel61Xg.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("The Weekend Away")
                .description("When her best friend vanishes during a girls' trip to Croatia, Beth races to figure out what happened. But each clue yields another unsettling deception.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/6MS0QEl7UK2gdFFbHfNwuYlsq4H.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Paranormal Activity: Next of Kin")
                .description("Margot, a documentary filmmaker, heads to a secluded Amish community in the hopes of learning about her long-lost mother and extended family. Following a string of strange occurrences and discoveries, she comes to realize this community may not be what it seems.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/bXAVveHiLotZbWdg3PKGhAzxYKP.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("The Conjuring: The Devil Made Me Do It")
                .description("Paranormal investigators Ed and Lorraine Warren encounter what would become one of the most sensational cases from their files. The fight for the soul of a young boy takes them beyond anything they'd ever seen before, to mark the first time in U.S. history that a murder suspect would claim demonic possession as a defense.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/xbSuFiJbbBWCkyCCKIMfuDCA4yV.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("Harry Potter and the Order of the Phoenix")
                .description("The rebellion begins! Lord Voldemort has returned, but the Ministry of Magic is doing everything it can to keep the wizarding world from knowing the truth � including appointing Ministry official Dolores Umbridge as the new Defence Against the Dark Arts professor at Hogwarts. When Umbridge refuses to teach practical defensive magic, Ron and Hermione convince Harry to secretly train a select group of students for the wizarding war that lies ahead. A terrifying showdown between good and evil awaits in this enthralling film version of the fifth novel in J.K. Rowling�s Harry Potter series. Prepare for battle!")
                .year(2007)
                .imageUrl("https://image.tmdb.org/t/p/w500/tRoHysNFsXC2r0JiBL6iNHELut7.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(14L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("Dragon Fury")
                .description("A group of soldiers are taken to the mountains of Wales to investigate a strange looking monster.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/6WcJ4cV2Y3gnTYp5zHu968TYmTJ.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("The Maze Runner")
                .description("Set in a post-apocalyptic world, young Thomas is deposited in a community of boys after his memory is erased, soon learning they're all trapped in a maze that will require him to join forces with fellow �runners� for a shot at escape.")
                .year(2014)
                .imageUrl("https://image.tmdb.org/t/p/w500/ode14q7WtDugFDp78fo9lCsmay9.jpg")
                .genres(genresMap)
                .rank(7)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(28L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(878L));
        genresMap.add(genres.get(53L));
        movies.add(MoviePayload.builder()
                .title("Knowing")
                .description("A teacher opens a time capsule that has been dug up at his son's elementary school; in it are some chilling predictions -- some that have already occurred and others that are about to -- that lead him to believe his family plays a role in the events that are about to unfold.")
                .year(2009)
                .imageUrl("https://image.tmdb.org/t/p/w500/nO9gTGDNdYnPr9ILKNQmk6EVTVR.jpg")
                .genres(genresMap)
                .rank(6)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(10751L));
        movies.add(MoviePayload.builder()
                .title("Erax")
                .description("Monstrous creatures leap from a magical storybook and unleash mayhem and mischief for Auntie Opal and her tween niece Nina in this spooky short film.")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/7b0pFRFDSkhYyNRKZjll5MqS3sh.jpg")
                .genres(genresMap)
                .rank(4)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(27L));
        genresMap.add(genres.get(53L));
        genresMap.add(genres.get(12L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("Black Water: Abyss")
                .description("An adventure-loving couple convince their friends to explore a remote, uncharted cave system in the forests of Northern Australia. With a tropical storm approaching, they abseil into the mouth of the cave, but when the caves start to flood, tensions rise as oxygen levels fall and the friends find themselves trapped. Unknown to them, the storm has also brought in a pack of dangerous and hungry crocodiles.")
                .year(2020)
                .imageUrl("https://image.tmdb.org/t/p/w500/pmAv14TPE2vKMIRrVeCd1Ll7K94.jpg")
                .genres(genresMap)
                .rank(5)
                .watched(false)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(16L));
        genresMap.add(genres.get(35L));
        genresMap.add(genres.get(9648L));
        genresMap.add(genres.get(10751L));
        genresMap.add(genres.get(14L));
        movies.add(MoviePayload.builder()
                .title("Straight Outta Nowhere: Scooby-Doo! Meets Courage the Cowardly Dog")
                .description("With Mystery, Inc. on the tail of a strange object in Nowhere, Kansas, the strange hometown of Eustice, Muriel, and Courage, the gang soon find themselves contending with a giant cicada monster and her winged warriors.")
                .year(2021)
                .imageUrl("https://image.tmdb.org/t/p/w500/uIdMpWrQ30SHPINsy7LcPFloyvO.jpg")
                .genres(genresMap)
                .rank(8)
                .watched(true)
                .build());
        genresMap = new HashSet<>();
        genresMap.add(genres.get(18L));
        genresMap.add(genres.get(9648L));
        movies.add(MoviePayload.builder()
                .title("El bucle de Latham")
                .description("")
                .year(2022)
                .imageUrl("https://image.tmdb.org/t/p/w500/vfpplWAbl50qHU1sO2C6Kd1lv6M.jpg")
                .genres(genresMap)
                .rank(4)
                .watched(false)
                .build());
        map.put(9648, movies); // Mystery

        return map;
    }
}

package first.securityapp.FirstSecurityProject.services;

import first.securityapp.FirstSecurityProject.models.Person;
import first.securityapp.FirstSecurityProject.repositories.PeopleRepository;
import first.securityapp.FirstSecurityProject.security.PersonDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeopleDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    public PeopleDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);
        if (!person.isPresent())
            throw new UsernameNotFoundException("User was not found!");
        return new PersonDetails(person.get());
    }
}
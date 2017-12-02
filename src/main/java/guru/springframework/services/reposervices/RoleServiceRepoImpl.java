package guru.springframework.services.reposervices;

import guru.springframework.domain.security.Authority;
import guru.springframework.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import guru.springframework.repositories.RoleRepository;

/**
 * Created by jt on 12/21/15.
 */
@Service
@Profile("springdatajpa")
public class RoleServiceRepoImpl implements RoleService {

    private RoleRepository roleRepsoitory;

    @Autowired
    public void setRoleRepsoitory(RoleRepository roleRepsoitory) {
        this.roleRepsoitory = roleRepsoitory;
    }

    @Override
    public List<Authority> listAll() {
        List<Authority> roles = new ArrayList<>();
        roleRepsoitory.findAll().forEach(roles::add); //fun with Java 8
        return roles;
    }

    @Override
    public Authority getById(Integer id) {
        return roleRepsoitory.findOne(id);
    }

    @Override
    public Authority saveOrUpdate(Authority domainObject) {
        return roleRepsoitory.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        roleRepsoitory.delete(id);
    }
}

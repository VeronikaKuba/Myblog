package code.club.blog.model.service;

import code.club.blog.model.Authority;
import code.club.blog.model.BlogUser;
import code.club.blog.model.repository.AuthorityRepository;
import code.club.blog.model.repository.BlogUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class BlogUserServiceImpl implements BlogUserService {
    private static final String DEFAULT_ROLE = "ROLE_USER";
    private final BCryptPasswordEncoder bcryptEncoder;
    private final BlogUserRepository blogUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public BlogUserServiceImpl(
            BCryptPasswordEncoder bcryptEncoder, BlogUserRepository blogUserRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            AuthorityRepository authorityRepository) {
        this.bcryptEncoder = bcryptEncoder;
        this.blogUserRepository = blogUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Optional<BlogUser> findByUsername(String username) {
        return blogUserRepository.findByUsername(username);

    }

    @Override
    public BlogUser saveNewBlogUser(BlogUser blogUser) throws RoleNotFoundException {
        System.err.println("saveNewBlogUser: " + blogUser);
        blogUser.setPassword(this.bcryptEncoder.encode(blogUser.getPassword()));
        blogUser.setEnabled(true);
        Optional<Authority> optionalAuthority = this.authorityRepository.findByAuthority(DEFAULT_ROLE);
        System.err.println("optionalAuthority: " + optionalAuthority);
        if (optionalAuthority.isPresent()) {
            Authority authority = optionalAuthority.get();
            Collection<Authority> authorities = Collections.singletonList(authority);
            blogUser.setAuthorities(authorities);
            System.err.println("blogUser after Roles: " + blogUser);
            return this.blogUserRepository.saveAndFlush(blogUser);
        } else {
            throw new RoleNotFoundException("Default role not found for blog user with username " + blogUser.getUsername());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

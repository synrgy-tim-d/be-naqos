package com.binar.kelompokd.models.entity.oauth;

import com.binar.kelompokd.enums.EProviders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "oauth_user")
public class Users implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,unique=true)
    private String username;

    @Column(length = 100, nullable = true)
    private String fullname;

    @Column(length = 13)
    private String phoneNumber;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String verifyToken;

    @JsonIgnore
    private Date expiredVerifyToken;

    @JsonIgnore
    @Column(length = 100, nullable = true)
    private String otp;

    @Column(name = "img_url")
    private String imgUrl = "https://xsgames.co/randomusers/avatar.php?g=pixel";

    @OneToOne
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    private Bank bankAccount;

    @Column(length = 6)
    @Enumerated(EnumType.STRING)
    private EProviders providers;

    @JsonIgnore
    private Date otpExpiredDate;

    @JsonIgnore
    private boolean enabled = true;

    @JsonIgnore
    @Column(name = "not_expired")
    private boolean accountNonExpired = true;

    @JsonIgnore
    @Column(name = "not_locked")
    private boolean accountNonLocked = true;

    @JsonIgnore
    @Column(name = "credential_not_expired")
    private boolean credentialsNonExpired = true;

    @JsonIgnore
    @ManyToMany(targetEntity = Roles.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "oauth_user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            }
    )
    private List<Roles> roles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public Date getExpiredVerifyToken() {
        return expiredVerifyToken;
    }

    public void setExpiredVerifyToken(Date expiredVerifyToken) {
        this.expiredVerifyToken = expiredVerifyToken;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getOtpExpiredDate() {
        return otpExpiredDate;
    }

    public void setOtpExpiredDate(Date otpExpiredDate) {
        this.otpExpiredDate = otpExpiredDate;
    }
}

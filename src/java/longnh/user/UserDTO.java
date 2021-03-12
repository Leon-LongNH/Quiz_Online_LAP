/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longnh.user;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author LongNH
 */
public class UserDTO implements Serializable{
    private String email;
    private String name;
    private String password;
    private boolean isAdmin;
    private boolean status;

    public UserDTO() {
    }

    public UserDTO(String email, String name, String password, boolean isAdmin, boolean status) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.email);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.password);
        hash = 59 * hash + (this.isAdmin ? 1 : 0);
        hash = 59 * hash + (this.status ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        if (this.isAdmin != other.isAdmin) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "email=" + email + ", name=" + name + ", password=" + password + ", isAdmin=" + isAdmin + ", status=" + status + '}';
    }
    
}

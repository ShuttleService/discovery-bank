package tech.ioco.discovery.bank.client;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int clientId;
    @NotNull
    private final String username;
    @NotNull
    private final String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private final Title title;
    @NotNull
    private final String name;
    @NotNull
    private final String surname;
    @NotNull
    private final Date dateOfBirth;
    @ManyToOne
    @NotNull
    private final ClientSubType clientSubType;
    @Enumerated(EnumType.STRING)
    @NotNull
    private final Role role;

    public Client(String username, String password, Title title, String name, String surname, Date dateOfBirth, ClientSubType clientSubType, Role role) {
        this.username = username;
        this.surname = surname;
        this.password = password;
        this.title = title;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.clientSubType = clientSubType;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Title getTitle() {
        return title;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getClientId() {
        return clientId;
    }


    @Override
    public String toString() {
        return name;
    }

    //This is to please the JPA Gods
    private Client() {
        this.username = null;
        this.surname = null;
        this.password = null;
        this.title = null;
        this.name = null;
        this.dateOfBirth = null;
        this.clientSubType = null;
        this.role = null;
    }
}

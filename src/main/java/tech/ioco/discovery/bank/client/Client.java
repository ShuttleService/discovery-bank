package tech.ioco.discovery.bank.client;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int clientId;
    private final String username;
    private final String password;
    @Enumerated(EnumType.STRING)
    private final Title title;
    private final String name;
    private final String surname;
    private final LocalDate dateOfBirth;
    @ManyToOne
    private final ClientSubType clientSubType;


    public Client(String username, String password, Title title, String name, String surname, LocalDate dateOfBirth, ClientSubType clientSubType) {
        this.username = username;
        this.surname = surname;
        this.password = password;
        this.title = title;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.clientSubType = clientSubType;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
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
    }
}

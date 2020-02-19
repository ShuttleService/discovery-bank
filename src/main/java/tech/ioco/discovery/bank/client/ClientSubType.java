package tech.ioco.discovery.bank.client;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ClientSubType {
    @Id
    private final String clientSubTypeCode;
    @ManyToOne
    private final ClientType clientType;
    private final String description;

    public ClientSubType(String clientSubTypeCode, ClientType clientType, String description) {
        this.clientSubTypeCode = clientSubTypeCode;
        this.clientType = clientType;
        this.description = description;
    }

    private ClientSubType() {
        this.clientSubTypeCode = null;
        this.clientType = null;
        this.description = null;
    }
}

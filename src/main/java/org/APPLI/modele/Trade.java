package org.APPLI.modele;

public class Trade {
    private int id;
    private String sender;
    private String senderTown;
    private String client;
    private String clientTown;

    public Trade(int _id, String _sender, String _sTown, String _client, String _cTown) {
        id = _id;
        sender =_sender;
        senderTown =_sTown;
        client =_client;
        clientTown =_cTown;
    }

    public void decrementIndex() {
        id--;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getSenderTown() {
        return sender+" ("+senderTown+")";
    }

    public String getClient() {
        return client;
    }
    public String getClientTown() {
        return client+" ("+clientTown+")";
    }


    public void setId(int _id) {
        id =_id;
    }

    public void setSender(String _sender) {
        sender=_sender;
    }

    public void setClient(String _client) {
        client =_client;
    }

    @Override
    public String toString() {
        return sender+" -> "+client;
    }
}
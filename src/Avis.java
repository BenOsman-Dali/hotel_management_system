import java.time.LocalDate;
import java.io.Serializable;

public class Avis implements Serializable {
    private Client client;
    private Hotel hotel;
    private int note; // 1-5
    private String commentaire;
    private LocalDate date;

    public Avis(Client client, Hotel hotel, int note, String commentaire) {
        this.client = client;
        this.hotel = hotel;
        this.note = note;
        this.commentaire = commentaire;
        this.date = LocalDate.now();
    }

    public int getNote() { return note; }
    public String getCommentaire() { return commentaire; }
    public LocalDate getDate() { return date; }
    public Client getClient() { return client; }
    public Hotel getHotel() { return hotel; }

    public double getNoteEnEtoiles() {
        return note;
    }
}
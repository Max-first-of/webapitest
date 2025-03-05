package model;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EsMunicipalityRequest {
    private String okato;
    @XmlElement
    public String getOkato(){
        return okato;
    }
    public void setOkato(String okato){
        this.okato = okato;
    }
}

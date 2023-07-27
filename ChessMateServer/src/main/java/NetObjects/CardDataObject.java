package NetObjects;

public class CardDataObject {
    int cardId;
    FieldDataObject field1,field2;

    public CardDataObject(){}

    public int getId() {
        return cardId;
    }

    public void setId(int id) {
        this.cardId = id;
    }

    public FieldDataObject getField1(){
        return field1;
    }

    public FieldDataObject getField2() {
        return field2;
    }

    public void setField1(FieldDataObject field1) {
        this.field1 = field1;
    }

    public void setField2(FieldDataObject field2) {
        this.field2 = field2;
    }
}

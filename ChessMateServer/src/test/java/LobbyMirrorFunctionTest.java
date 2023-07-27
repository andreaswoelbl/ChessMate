
import NetObjects.FieldDataObject;
import com.esotericsoftware.kryonet.Connection;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LobbyMirrorFunctionTest {
    FieldDataObject field_00;
    FieldDataObject field_77;
    FieldDataObject field_23;
    FieldDataObject field_54;
    public void initFields(){
        field_00 = new FieldDataObject();
        field_00.setX(0);
        field_00.setY(0);
        field_77 = new FieldDataObject();
        field_00.setX(7);
        field_00.setY(7);
        field_23 = new FieldDataObject();
        field_23.setX(2);
        field_23.setY(3);
        field_54 = new FieldDataObject();
        field_54.setX(5);
        field_54.setY(4);
    }

    @DisplayName("00to77Test")
    @Test
    public void mirrorFuncTest(){
        initFields();
        FieldDataObject resultingField = Lobby.mirrorFunc(field_00);
        assertEquals(field_77.getX(),resultingField.getX());
        assertEquals(field_77.getY(),resultingField.getY());
    }
    @DisplayName("77to00Test")
    @Test
    public void mirrorFuncTest2(){
        initFields();
        FieldDataObject resultingField = Lobby.mirrorFunc(field_77);
        assertEquals(field_00.getX(),resultingField.getX());
        assertEquals(field_00.getY(),resultingField.getY());
    }
    @DisplayName("23to54Test")
    @Test
    public void mirrorFuncTest3(){
        initFields();
        FieldDataObject resultingField = Lobby.mirrorFunc(field_23);
        assertEquals(field_54.getX(),resultingField.getX());
        assertEquals(field_54.getY(),resultingField.getY());
    }
    @DisplayName("54to23Test")
    @Test
    public void mirrorFuncTest4(){
        initFields();
        FieldDataObject resultingField = Lobby.mirrorFunc(field_54);
        assertEquals(field_23.getX(),resultingField.getX());
        assertEquals(field_23.getY(),resultingField.getY());
    }

    @DisplayName("Failing test 44to33Test")
    @Test
    public void mirrorFuncTest5(){
        initFields();
        FieldDataObject resultingField = Lobby.mirrorFunc(field_54);
        assertNotEquals(field_00.getX(),resultingField.getX());
        assertNotEquals(field_00.getY(),resultingField.getY());
    }




}

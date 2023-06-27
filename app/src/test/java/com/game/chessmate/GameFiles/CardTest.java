package com.game.chessmate.GameFiles;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class CardTest {
    String n0="Cowardice";
    String n1="Crusade";
    String n2="Dark Mirror";
    String n3="Death Dance";
    String n4="Disintegration";
    String n5="Champion";
    String n6="Rebirth";
    String n7="Revelation";
    String n8="Long Jump";
    String n9="Lost Castle";
    String n10="Man of Straw";
    String n11="Mystic Shield";
    String n12="Forbidden City";
    String n13="Holy Quest";
    String n14="Bombard";
    String n15="Coup";
    String n16="Hand of Fate";
    String n17="Funeral Pyre";
    String n18="Martyr";
    String n19="Confabulation";
    String n20="Fog of War";
    String n21="Spoils of War";
    String n22="Think Again";
    String n23="Vulture";
    String n24="Abduction";

    Card card;

    @Test
    public void testInit(){

        card=new Card(0);
        assertEquals(n0,card.getName());

        card=new Card(1);
        assertEquals(n1,card.getName());

        card=new Card(2);
        assertEquals(n2,card.getName());

        card=new Card(3);
        assertEquals(n3,card.getName());

        card=new Card(4);
        assertEquals(n4,card.getName());

        card=new Card(5);
        assertEquals(n5,card.getName());

        card=new Card(6);
        assertEquals(n6,card.getName());

        card=new Card(7);
        assertEquals(n7,card.getName());

        card=new Card(8);
        assertEquals(n8,card.getName());

        card=new Card(9);
        assertEquals(n9,card.getName());

        card=new Card(10);
        assertEquals(n10,card.getName());

        card=new Card(11);
        assertEquals(n11,card.getName());

        card=new Card(12);
        assertEquals(n12,card.getName());

        card=new Card(13);
        assertEquals(n13,card.getName());

        card=new Card(14);
        assertEquals(n14,card.getName());

        card=new Card(15);
        assertEquals(n15,card.getName());

        card=new Card(16);
        assertEquals(n16,card.getName());

        card=new Card(17);
        assertEquals(n17,card.getName());

        card=new Card(18);
        assertEquals(n18,card.getName());

        card=new Card(19);
        assertEquals(n19,card.getName());

        card=new Card(20);
        assertEquals(n20,card.getName());

        card=new Card(21);
        assertEquals(n21,card.getName());

        card=new Card(22);
        assertEquals(n22,card.getName());

        card=new Card(23);
        assertEquals(n23,card.getName());

        card=new Card(24);
        assertEquals(n24,card.getName());
    }

    @Test
    public void testInitFailure(){
        assertThrows(IllegalArgumentException.class, () -> {new Card(25);});
    }

    @Test
    public void testContinuingTillEnd(){
        card=new Card(15);
        assertTrue(card.isContinuingUntilEnd());

        card=new Card(0);
        assertFalse(card.isContinuingUntilEnd());
    }

    @Test
    public void testContinuingTillCaptured(){
        card=new Card(19);
        assertTrue(card.isContinuingUntilCaptured());

        card=new Card(0);
        assertFalse(card.isContinuingUntilCaptured());
    }

    @Test
    public void testGetDesc(){
        card=new Card(0);
        assertEquals("Move one of your opponent's pawns one or two squares backward. It may not enter or cross an occupied square.",card.getDesc());
    }

    @Test
    public void testGetUseCase(){
        card=new Card(0);
        assertEquals("[i] Play this card immediately after your move",card.getUseCase());
    }

    @Test
    public void testOwned(){
        card=new Card(0);
        assertFalse(card.isOwned());

        card.setOwned();

        assertTrue(card.isOwned());
    }
}

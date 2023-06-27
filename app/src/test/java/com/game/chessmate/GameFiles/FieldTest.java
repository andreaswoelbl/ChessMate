package com.game.chessmate.GameFiles;

public class FieldTest {
    Field field;

 /*   @Mock
    Context context;

    @Mock
    ChessPiece bishop;

    @Mock
    Rect rectangle;

    @Before
    public void init(){
        context= Mockito.mock(Context.class);
        bishop=Mockito.mock(Bishop.class);
        rectangle=Mockito.mock(Rect.class);
        field = new Field(0, 0, context, null);
    }

    @Test
    public void hasPieceTrueTest() {
        field.setCurrentPiece(bishop);

        assertTrue(field.hasPiece());
    }

    @Test
    public void hasPieceFalseTest(){
        field.setCurrentPiece(null);
        assertFalse(field.hasPiece());
    }

    @Test
    public void getCurrentPieceTest(){
        field.setCurrentPiece(bishop);

        assertEquals(bishop,field.getCurrentPiece());

        field.setCurrentPiece(null);
        assertEquals(null,field.getCurrentPiece());
    }

    @Test
    public void getChessCoordinatesTest(){

        assertEquals("A1",field.getChessCoordinates());
    }

    @Test
    public void getXY(){
        assertEquals(0,field.getFieldX());
        assertEquals(0,field.getFieldY());
    }

   /* @Test
    public void ifSetupRectangle_ThenRectangleNotNull() {
        Rect rect = new Rect();
        field.setupRectangle(rect);
        assertNotNull("Rectangle of Field is null.", field.getRectangle());
    }

    @Test
    public void ifSetupRectangle_ThenRectangleHasCorrectOffsets() {
        Rect rect = new Rect();
        field.setupRectangle(rect);
        rect = field.getRectangle();
        assertEquals(0,rect.top);
        assertEquals(0,rect.left);
        assertEquals(0,rect.bottom);
        assertEquals(0,rect.right);
    }

 /*   @Test
    public void setLegalMoveColourTest() {
        field.setAsLegal();

        assertEquals(Color.YELLOW, field.color);
    }*/

}

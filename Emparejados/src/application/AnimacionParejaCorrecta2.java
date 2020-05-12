package application;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class AnimacionParejaCorrecta2 extends Animaciones {

	
	public AnimacionParejaCorrecta2() {
		super();
	}
	
    
	public void crearAnimacion(){
        stackPane.setDisable(true);
        Node card1 = FlipAdelanteCard(imagen1);
        Node card2 = FlipAdelanteCard(imagen2);

        RotateTransition firstRotator1 = createFirstRotator(card1);
        RotateTransition secondRotator1 = createSecondRotator(card1);

        RotateTransition firstRotator2 = createFirstRotator(card2);
        RotateTransition secondRotator2 = createSecondRotator(card2);

        firstRotator1.play();
        firstRotator1.setOnFinished(e -> {
          //  imagen1.setImage(baraja.getImagenDorso());
               secondRotator1.play();
        });

        firstRotator2.play();
        firstRotator2.setOnFinished(e -> {
           // imagen2.setImage(baraja.getImagenDorso());
               secondRotator2.play();
        });

        TranslateTransition firstTranslation1 = createIncorrectTranslation(card1);
        TranslateTransition firstTranslation2 = createIncorrectTranslation(card2);

        firstTranslation1.play();
        firstTranslation2.play();

        firstTranslation2.setOnFinished(e -> {
            stackPane.setDisable(false);
        });

    }

}

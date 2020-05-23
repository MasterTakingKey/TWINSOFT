package application;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;

public class AnimacionFinTurno extends Animaciones{
	
	public AnimacionFinTurno() {
		super();
	}
	
    
	public void crearAnimacion(){
        stackPane.setDisable(true);
        Node card1 = FlipAdelanteCard(imagen1);

        RotateTransition firstRotator1 = createFirstRotator(card1);
        RotateTransition secondRotator1 = createSecondRotator(card1);

        firstRotator1.play();
        firstRotator1.setOnFinished(e -> {
            imagen1.setImage(baraja.getImagenDorso());
               secondRotator1.play();
        });

        TranslateTransition firstTranslation1 = createIncorrectTranslation(card1);

        firstTranslation1.play();

        firstTranslation1.setOnFinished(e -> {
            stackPane.setDisable(false);
        });

    }
}

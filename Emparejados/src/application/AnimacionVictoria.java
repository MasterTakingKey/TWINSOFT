package application;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;

public class AnimacionVictoria extends Animaciones {

	
	public AnimacionVictoria() {
		super();
	}
	
    
	public void crearAnimacion(){
        //pane.setDisable(true);
        Node imagen = FlipAdelanteCard(imagen1);
        TranslateTransition firstTranslation1 = victoryAnimation(imagen);

        firstTranslation1.play();

        firstTranslation1.setOnFinished(e -> {
            //pane.setDisable(false);
        });

    }

}

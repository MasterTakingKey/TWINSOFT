package application;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Animaciones {

	public StackPane stackPane;
	
	public Baraja baraja;
	
	public Animaciones(StackPane newStackPane, Baraja newBaraja) {
		stackPane = newStackPane;
		baraja = newBaraja;
	}
	
    private Node FlipAdelanteCard(ImageView card) {
        return card;
    }
    
    private RotateTransition createFirstRotator(Node card) {
   
        RotateTransition firstRotator = new RotateTransition(Duration.millis(200), card);
        
        firstRotator.setAxis(Rotate.Y_AXIS);
        firstRotator.setFromAngle(180);
        firstRotator.setToAngle(91);
        firstRotator.setInterpolator(Interpolator.LINEAR);
        firstRotator.setCycleCount(1);
        
        return firstRotator;
    }
    private RotateTransition createSecondRotator(Node card) {
    	
        RotateTransition secondRotator = new RotateTransition(Duration.millis(200), card);
        
        secondRotator.setAxis(Rotate.Y_AXIS);
        secondRotator.setFromAngle(90);
        secondRotator.setToAngle(0);
        secondRotator.setInterpolator(Interpolator.LINEAR);
        secondRotator.setCycleCount(1);

        return secondRotator;
    }
    
    private TranslateTransition createIncorrectTranslation(Node card) {
    	
    	TranslateTransition translation = new TranslateTransition(Duration.millis(300), card);
    	
    	translation.setByY(50);
    	translation.setByY(-50);
    	translation.setAutoReverse(true);
    	translation.setCycleCount(2);

    	return translation;
    }
    
    private TranslateTransition createCorrectTranslation(Node card) {
    	
    	TranslateTransition translation = new TranslateTransition(Duration.millis(200), card);

    	translation.setByY(50);
    	translation.setByY(-50);
    	translation.setAutoReverse(true);
    	translation.setCycleCount(4);

    	return translation;

    }

    public void clickCartaAnimacion(ImageView imagenSeleccionada, Carta cartaSeleccionada) {
    	stackPane.setDisable(true);
    	Node card = FlipAdelanteCard(imagenSeleccionada);

        RotateTransition firstRotator = createFirstRotator(card);
        RotateTransition secondRotator = createSecondRotator(card);

        firstRotator.play();
        firstRotator.setOnFinished(e -> {
            imagenSeleccionada.setImage(cartaSeleccionada.imagenFrente);
               secondRotator.play();
        });
        secondRotator.setOnFinished(e -> {
            stackPane.setDisable(false);
     });
    }

    public void parejaCorrectaAnimacion(ImageView carta1, ImageView carta2) {
    	stackPane.setDisable(true);
    	Node card1 = FlipAdelanteCard(carta1);
	    Node card2 = FlipAdelanteCard(carta2);

	    TranslateTransition firstTranslation1 = createCorrectTranslation(card1);
	    TranslateTransition firstTranslation2 = createCorrectTranslation(card2);

	    firstTranslation1.play();
	    firstTranslation2.play();

	    firstTranslation2.setOnFinished(e -> {
	    	stackPane.setDisable(false);
	    });

	  }


    public void parejaIncorrectaAnimacion(ImageView carta1, ImageView carta2){
    	stackPane.setDisable(true);
	    Node card1 = FlipAdelanteCard(carta1);
	    Node card2 = FlipAdelanteCard(carta2);

	    RotateTransition firstRotator1 = createFirstRotator(card1);
	    RotateTransition secondRotator1 = createSecondRotator(card1);

	    RotateTransition firstRotator2 = createFirstRotator(card2);
	    RotateTransition secondRotator2 = createSecondRotator(card2);

	    firstRotator1.play();
	    firstRotator1.setOnFinished(e -> {
	        carta1.setImage(baraja.getImagenDorso());
	           secondRotator1.play();
	    });	    

	    firstRotator2.play();
	    firstRotator2.setOnFinished(e -> {
	        carta2.setImage(baraja.getImagenDorso());
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

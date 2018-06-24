
/**
 * Read and detect motion from a PIR Sensor using Raspberry Pi3
 * and Pi4j Java Library
 * @author Ugwumsinachi Nnadi 
 * @version 24/6/2018
 */

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.*;


public class PIRSensor
{
   

    /**
     * Constructor for objects of class PIRSensor
     */
    public PIRSensor()
    {
        // initialise instance variables
        
    }
    
    public static void main(String[] args) throws InterruptedException{

        // Create Gpio Controller
        GpioController gpio = GpioFactory.getInstance();

        // provision the necessary pins in order to use it
        // provision PIR pin as input and LedPin as output
        GpioPinDigitalInput pir = gpio.provisionDigitalInputPin(RaspiPin.GPIO_29);
        GpioPinDigitalOutput ledPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
        
        // Ensure the GPIO pins states are not active or leaving some activity engaged 
        //if the program is shutdown.
        pir.setShutdownOptions(true);
        
        // monitor for Pin State changes
        // if state is high, ON Led
        pir.addListener(new GpioPinListenerDigital(){
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event){
                System.out.println(event.getState());
                
                // turn led ON if pin attached to PIR sensor is high
                if(event.getState().isHigh() ){
                    ledPin.high();
                }else{
                    ledPin.low();
                }
                
            }
        });
        // lets get this program running until user aborts (CTRL-C)
        while(true){
                Thread.sleep(500);
            }

        // stop all GPIO activity/threads by shutting down the GPIO controller  
        gpio.shutdown();  
        
    
    }
    
    
}

import com.scottpreston.javarobot.chapter2.WebSerialClient;
import com.scottpreston.javarobot.chapter5.MicrosoftVoice;
import com.scottpreston.javarobot.chapter7.Navigation;

try {
	WebSerialClient com = new WebSerialClient("10.10.10.99", "8080","1");
	Navigation nav = new Navigation(com);
	nav.changeHeading(SimpleNavigation.NORTH);
	MicrosoftVoice voice = MicrosoftVoice.getInstance();
	voice.speak("I am facing north now.");
	println("done");
} catch (Exception e) {
     e.printStackTrace();
     System.exit(1);
}

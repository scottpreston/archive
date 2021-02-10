package com.scottpreston.javarobot.chapter5;

public class TTSCompare {

	public static final int JAVA_VOICE = 0;
	public static final int FREETTS_VOICE = 1;
	public static final int MICROSOFT_VOICE = 2;

	public JVoice getVoice(int voiceID) throws Exception {
		
		JVoice voice;
		
		if (voiceID == FREETTS_VOICE) {
			voice = new FreeTTSVoice(FreeTTSVoice.VOICE_KEVIN_16);
		} else if (voiceID == MICROSOFT_VOICE) {
			voice = MicrosoftVoice.getInstance();
		} else {
			voice = new JavaVoice();
		}
		return voice;
	}

	// simple program to test all classes and compare quality
	public static void main(String[] args) {
		try {

			TTSCompare tts = new TTSCompare();
			// java voice
			JVoice voice1 = tts.getVoice(TTSCompare.JAVA_VOICE);
			// free tts voice
			JVoice voice2 = tts.getVoice(TTSCompare.FREETTS_VOICE);
			// microsoft voice
			JVoice voice3 = tts.getVoice(TTSCompare.MICROSOFT_VOICE);
			// open all of these
			voice1.open();
			voice2.open();
			voice3.open();
			// speak some text
			voice1.speak("Java Voice... Hello World!");
			voice2.speak("Free TTS Voice... Hello World!");
			voice3.speak("Microsoft Voice... Hello World!");
			// close them
			voice1.close();
			voice2.close();
			voice3.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
}
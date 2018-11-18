package br.com.fatecmogidascruzes.config.captcha;

public interface ReCaptchaService {

	String verifyRecaptcha(String ip, String recaptchaResponse);

}
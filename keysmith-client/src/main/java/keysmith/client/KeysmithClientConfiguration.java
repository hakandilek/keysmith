package keysmith.client;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.client.JerseyClientConfiguration;
import com.yammer.dropwizard.config.Configuration;

public class KeysmithClientConfiguration extends Configuration {

	@Valid
	@NotNull
	private String keyId;

	@Valid
	@NotNull
	private String message;

	@Valid
	@NotNull
	private String publicKeyAlgorithm;

	@Valid
	@NotNull
	private String publicKeyTransformation;

	@Valid
	@NotNull
	private String secretKeyAlgorithm;

	@Valid
	@NotNull
	private String secretKeyTransformation;

	@Valid
	@NotNull
	private String secretSeed;
	
	@Valid
	@NotNull
	private Integer keySize;

	@Valid
	@NotNull
	private String keysmithServer;

	@Valid
	@NotNull
	private String messengerServer;

	@Valid
	@NotNull
	@JsonProperty
	private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

	public JerseyClientConfiguration getJerseyClientConfiguration() {
		return httpClient;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPublicKeyAlgorithm() {
		return publicKeyAlgorithm;
	}

	public void setPublicKeyAlgorithm(String publicKeyAlgorithm) {
		this.publicKeyAlgorithm = publicKeyAlgorithm;
	}

	public String getPublicKeyTransformation() {
		return publicKeyTransformation;
	}

	public void setPublicKeyTransformation(String publicKeyTransformation) {
		this.publicKeyTransformation = publicKeyTransformation;
	}

	public String getSecretKeyAlgorithm() {
		return secretKeyAlgorithm;
	}

	public void setSecretKeyAlgorithm(String secretKeyAlgorithm) {
		this.secretKeyAlgorithm = secretKeyAlgorithm;
	}

	public String getSecretKeyTransformation() {
		return secretKeyTransformation;
	}

	public void setSecretKeyTransformation(String secretKeyTransformation) {
		this.secretKeyTransformation = secretKeyTransformation;
	}

	public Integer getKeySize() {
		return keySize;
	}

	public void setKeySize(Integer keySize) {
		this.keySize = keySize;
	}

	public String getKeysmithServer() {
		return keysmithServer;
	}

	public void setKeysmithServer(String keysmithServer) {
		this.keysmithServer = keysmithServer;
	}

	public String getMessengerServer() {
		return messengerServer;
	}

	public void setMessengerServer(String messengerServer) {
		this.messengerServer = messengerServer;
	}

	public String getSecretSeed() {
		return secretSeed;
	}

	public void setSecretSeed(String secretSeed) {
		this.secretSeed = secretSeed;
	}

}

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
	private String algorithm;

	@Valid
	@NotNull
	private String cipherTransformation;

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

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getCipherTransformation() {
		return cipherTransformation;
	}

	public void setCipherTransformation(String cipherTransformation) {
		this.cipherTransformation = cipherTransformation;
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

}

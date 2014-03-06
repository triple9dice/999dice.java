package dice.client.web;

import javax.json.JsonObject;

public final class GetDepositAddressResponse extends DiceResponse {
	String depositAddress;

	@Override
	void setRawResponse(JsonObject resp) {
		super.setRawResponse(resp);

		if (resp.containsKey("Address") && !resp.isNull("Address")) {
			success = true;
			depositAddress = resp.getString("Address");
		}
	}

	public String getDepositAddress() {
		return depositAddress;
	}

}

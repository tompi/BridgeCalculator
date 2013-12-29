package com.brisco.BridgeCalculator;

import android.content.Context;

import com.brisco.Game.Contract;
import com.brisco.Game.Suit;

public class Mapping {
	public static String GetContractString(Contract contract, Context context) {
		if (contract.Level == 0) {
			return "Pass";
		}
		StringBuffer ret = new StringBuffer();
		ret.append(contract.Level);
		ret.append(GetStringFromSuit(contract.Suit, context));
		if (contract.ReDoubled) {
			ret.append("XX");
		} else if (contract.Doubled) {
			ret.append("X");
		}
		return ret.toString();
	}

	private static String GetStringFromSuit(Suit suit, Context context) {
		switch (suit) {
		case Notrump:
			return context.getString(R.string.symbol_notrump);
		case Spades:
			return context.getString(R.string.symbol_spades);
		case Hearts:
			return context.getString(R.string.symbol_hearts);
		case Diamonds:
			return context.getString(R.string.symbol_diamonds);
		default:
			return context.getString(R.string.symbol_clubs);
		}
	}
}

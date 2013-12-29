package com.brisco.BridgeCalculator;

import android.app.Activity;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.brisco.Game.Contract;
import com.brisco.Game.Direction;
import com.brisco.Game.Suit;

public class CalculatorViews extends ViewsHelper {
	private Contract _contract;
	private static final Suit[] _suits = { com.brisco.Game.Suit.Clubs,
			com.brisco.Game.Suit.Diamonds, com.brisco.Game.Suit.Hearts,
			com.brisco.Game.Suit.Spades, com.brisco.Game.Suit.Notrump };

	public CalculatorViews(Activity activity) {
		super(activity);
		_contract = new Contract();
		// Level
		Level[0] = (Button) Find(R.id.ContractLevelPass);
		Level[1] = (Button) Find(R.id.ContractLevel1);
		Level[2] = (Button) Find(R.id.ContractLevel2);
		Level[3] = (Button) Find(R.id.ContractLevel3);
		Level[4] = (Button) Find(R.id.ContractLevel4);
		Level[5] = (Button) Find(R.id.ContractLevel5);
		Level[6] = (Button) Find(R.id.ContractLevel6);
		Level[7] = (Button) Find(R.id.ContractLevel7);

		// Suit
		Suit[0] = (Button) Find(R.id.ContractSuitClubs);
		Suit[1] = (Button) Find(R.id.ContractSuitDiamonds);
		Suit[2] = (Button) Find(R.id.ContractSuitHearts);
		Suit[3] = (Button) Find(R.id.ContractSuitSpades);
		Suit[4] = (Button) Find(R.id.ContractSuitNT);

		Doubled = (ToggleButton) Find(R.id.ContractDoubled);
		ReDoubled = (ToggleButton) Find(R.id.ContractReDoubled);
		Vulnerable = (ToggleButton) Find(R.id.vulnerable);
		Tricks = (SeekBar) Find(R.id.TricksSeekBar);
		TricksLabel = (TextView) Find(R.id.TricksLabel);
		ContractLabel = (TextView) Find(R.id.contractLabel);
		TricksPlus = (Button) Find(R.id.contractPlusTrick);
		TricksMinus = (Button) Find(R.id.contractMinusTrick);
	}

	public Button[] Level = new Button[8];
	public Button[] Suit = new Button[5];
	public ToggleButton Doubled;
	public ToggleButton ReDoubled;
	public ToggleButton Vulnerable;
	public SeekBar Tricks;
	public Button TricksPlus;
	public Button TricksMinus;
	public TextView TricksLabel;
	public TextView ContractLabel;

	public Suit GetSuit() {
		for (int i = 0; i < Suit.length; i++) {
			if (!Suit[i].isEnabled()) {
				return _suits[i];
			}
		}
		return null;
	}

	public void ToggleToggleButtons(TextView[] buttons, int clickedButtonID) {
		for (TextView b : buttons) {
			if (b.getId() == clickedButtonID) {
				b.setEnabled(false);
			} else {
				b.setEnabled(true);
			}

		}
	}

	public Contract GetContract() {
		_contract.Doubled = Doubled.isChecked();
		_contract.ReDoubled = ReDoubled.isChecked();
		_contract.Tricks = Tricks.getProgress();
		_contract.Player = Direction.North;
		_contract.Level = GetLevel();
		_contract.Suit = GetSuit();
		return _contract;
	}

	public void SetContract(Contract contract, boolean vulnerable) {
		Vulnerable.setChecked(vulnerable);
		Doubled.setChecked(contract.Doubled);
		ReDoubled.setChecked(contract.ReDoubled);
		Tricks.setProgress(contract.Tricks);
		SetLevel(contract.Level);
		SetSuit(contract.Suit);
	}

	private void SetSuit(com.brisco.Game.Suit suit) {
		boolean enabled = true;
		for (int i = 0; i < _suits.length; i++) {
			enabled = true;
			if (_suits[i] == suit) {
				enabled = false;
			}
			((TextView) Suit[i]).setEnabled(enabled);
		}
	}

	private void SetLevel(int level) {
		boolean enabled = true;
		for (int i = 0; i < Level.length; i++) {
			enabled = true;
			if (i == level) {
				enabled = false;
			}
			((TextView) Level[i]).setEnabled(enabled);
		}
	}

	public int GetLevel() {
		for (int i = 0; i < 8; i++) {
			if (!Level[i].isEnabled()) {
				return i;
			}
		}
		return -1;
	}
}

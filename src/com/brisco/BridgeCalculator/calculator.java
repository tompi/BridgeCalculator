package com.brisco.BridgeCalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.brisco.Game.Contract;
import com.brisco.Game.Direction;
import com.brisco.Game.Suit;
import com.brisco.Score.Calculator;

public class calculator extends Activity implements OnSeekBarChangeListener,
		OnCheckedChangeListener,
		android.widget.RadioGroup.OnCheckedChangeListener, OnClickListener {
	private CalculatorViews _views = null;
	private Contract _contract = new Contract();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		_views = new CalculatorViews(this);

		_views.SetContract(new Contract(2, Suit.Spades, false, false,
				Direction.North, 8), false);
		updateScore();

		_views.Tricks.setOnSeekBarChangeListener(this);
		_views.Doubled.setOnClickListener(this);
		_views.ReDoubled.setOnClickListener(this);
		_views.Vulnerable.setOnClickListener(this);

		for (TextView tv : _views.Suit) {
			tv.setOnClickListener(this);
		}
		for (TextView tv : _views.Level) {
			tv.setOnClickListener(this);
		}

		_views.TricksMinus.setOnClickListener(this);
		_views.TricksPlus.setOnClickListener(this);

		_contract.Player = Direction.North;
	}

	private void updateScore() {
		_contract = _views.GetContract();
		boolean vulnerable = _views.Vulnerable.isChecked();
		int score = Calculator.GetNorthSouthPoints(_contract, vulnerable);
		String contractString = Mapping.GetContractString(_contract, this);
		if (_contract.Level > 0) {
			int diff = _contract.Tricks - 6 - _contract.Level;
			if (diff > 0) {
				contractString += " (+" + Integer.toString(diff) + ")";
			} else if (diff < 0) {
				contractString += " (" + Integer.toString(diff) + ")";
			}
		}
		if (vulnerable) {
			contractString += " Vuln.";
		}
		_views.ContractLabel.setText(contractString + " = "
				+ Integer.toString(score));
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		_views.TricksLabel.setText("Tricks: " + Integer.toString(progress));
		updateScore();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		updateScore();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		updateScore();

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.contractPlusTrick) {
			_views.Tricks.incrementProgressBy(1);
		} else if (id == R.id.contractMinusTrick) {
			_views.Tricks.incrementProgressBy(-1);

		} else {
			int tricks = 13;
			switch (id) {
			case R.id.ContractLevelPass:
				tricks = 6;
			case R.id.ContractLevel1:
				tricks--;
			case R.id.ContractLevel2:
				tricks--;
			case R.id.ContractLevel3:
				tricks--;
			case R.id.ContractLevel4:
				tricks--;
			case R.id.ContractLevel5:
				tricks--;
			case R.id.ContractLevel6:
				tricks--;
			case R.id.ContractLevel7:
				// Disabled checked, and uncheck others(when unchecking we
				// trigger
				// oncheckedchanged, but do nothing in that case...)
				_views.ToggleToggleButtons(_views.Level, id);
				_views.Tricks.setProgress(tricks);
				break;
			case R.id.ContractSuitClubs:
			case R.id.ContractSuitDiamonds:
			case R.id.ContractSuitHearts:
			case R.id.ContractSuitSpades:
			case R.id.ContractSuitNT:
				_views.ToggleToggleButtons(_views.Suit, id);
			}
		}

		updateScore();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_exit:
			finish();
			return true;
		case R.id.menu_about:
			new AlertDialog.Builder(this).setView(GetAboutView())
					.setPositiveButton(getText(R.string.OK), null).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private View GetAboutView() {
		LayoutInflater vi = LayoutInflater.from(this);
		View ret = vi.inflate(R.layout.about, null);
		return ret;
	}

}
package entity.membre;

import commun.Statut;

public abstract class Adherent {
	protected abstract int verifierStatusExistant();
	public abstract String suppressionMembrePossible();
	protected abstract Membre testerChangeStatus(Statut status);
}

package com.oracle.jcclassic.samples.wallet;

public class Nota {
	public short codMaterie;
	public short[] notaMaterie = new short[20];
	public short[] zi = new short[20];
	public short[] luna = new short[20];
	public short[] an = new short[20];
	short counterNote=0;
	
	public Nota(short codMaterie) {
		this.codMaterie = codMaterie;
	}
	
	public void addNota(short nota,short zi,short luna, short an) {
		notaMaterie[counterNote] = nota;
		this.zi[counterNote] = zi;
		this.luna[counterNote] = luna;
		this.an[counterNote] = an;
		counterNote++;
	}
}

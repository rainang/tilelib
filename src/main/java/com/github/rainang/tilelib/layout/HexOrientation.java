package com.github.rainang.tilelib.layout;

import static java.lang.Math.sqrt;

public enum HexOrientation
{
	POINTY(new double[] {
			sqrt(3.0),
			sqrt(3.0) / 2.0,
			0.0,
			3.0 / 2.0
	}, new double[] {
			sqrt(3.0) / 3.0,
			-1.0 / 3.0,
			0.0,
			2.0 / 3.0
	}, 0.5),
	FLAT(new double[] {
			3.0 / 2.0,
			0.0,
			sqrt(3.0) / 2.0,
			sqrt(3.0)
	}, new double[] {
			2.0 / 3.0,
			0.0,
			-1.0 / 3.0,
			sqrt(3.0) / 3.0
	}, 0);
	
	final double[] f;
	final double[] b;
	final double startAngle;
	
	HexOrientation(double[] f, double[] b, double startAngle)
	{
		this.f = f;
		this.b = b;
		this.startAngle = startAngle;
	}
}
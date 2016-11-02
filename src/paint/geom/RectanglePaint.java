package paint.geom;

import java.util.Random;

import javafx.scene.paint.Color;
import paint.geom.util.ShapeFactory;
import paint.shapes.util.RectangleProperties;
import paint.shapes.util.ShapeProperties;

public class RectanglePaint extends PolygonPaint implements Cloneable {
	private Point upperLeftPoint;
	private double width;
	private double height;
	private static final int UPPER_LEFT_X = 0;
	private static final int UPPER_LEFT_Y = 1;
	private static final int BOTTOM_RIGHT_X = 2;
	private static final int BOTTOM_RIGHT_Y = 3;
	public static final String KEY = "rectangle";

	static {
		ShapeFactory.getInstance().registerShape(KEY, RectanglePaint.class);
	}

	public RectanglePaint(Point upperLeft,
			double width, double height) {
		super(upperLeft,
				new Point(upperLeft.getX()
						, upperLeft.getY() + height),
				new Point(upperLeft.getX() + width
						, upperLeft.getY() + height),
				new Point(upperLeft.getX() + width,
						upperLeft.getY()));
		setUpperLeftPoint(upperLeft);
		super.setBorderColor(Color.BLACK);
		super.fill(Color.TRANSPARENT);
		this.width = width;
		this.height = height;
		polygon.setId(KEY + new Random().nextInt());
	}

	public RectanglePaint(double... properties) {
		this(new Point(Math.min(properties[UPPER_LEFT_X], properties[BOTTOM_RIGHT_X]),
				Math.min(properties[UPPER_LEFT_Y], properties[BOTTOM_RIGHT_Y])),
				Math.abs(properties[UPPER_LEFT_X] - properties[BOTTOM_RIGHT_X]),
				Math.abs(properties[UPPER_LEFT_Y] - properties[BOTTOM_RIGHT_Y]));
	}

	public RectanglePaint(ShapeProperties properties) {
		this(properties.getPoint1().getX(),
				properties.getPoint1().getY(),
				properties.getPoint2().getX(),
				properties.getPoint2().getY());
		polygon.setStroke(properties.getStrokeColor());
		polygon.setFill(properties.getFillColor());
		polygon.setStrokeWidth(properties.getStrokeWidth());
		polygon.setRotate(properties.getRotation());
		polygon.setTranslateX(properties.getTranslateX());
		polygon.setTranslateY(properties.getTranslateY());
	}

	@Override
	public String getIconUrl() {
		return null;
	}

	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}

	@Override
	public void resize(double x1, double y1, double x2, double y2) {
		for (int i = 0;
				i < super.polygon.getPoints().size() - 1; i += 2) {
			double x = super.polygon.getPoints().get(i);
			double y = super.polygon.getPoints().get(i + 1);
			if (x == x1 && y == y1) {
				super.polygon.getPoints().set(i, x2);
				super.polygon.getPoints().set(i + 1, y2);
			} else if (x == x1) {
				super.polygon.getPoints().set(i, x2);
			} else if (y == y1) {
				super.polygon.getPoints().set(i + 1, y2);
			}
		}
	}

	@Override
	public RectanglePaint clone() throws CloneNotSupportedException {
		RectanglePaint newObject = new RectanglePaint(upperLeftPoint.clone(), width, height);
		newObject.polygon.setTranslateX(polygon.getTranslateX());
		newObject.polygon.setTranslateY(polygon.getTranslateY());
		newObject.polygon.setRotate(polygon.getRotate());
		Color col = (Color) polygon.getFill();
		newObject.polygon.setFill(new Color(col.getRed(), col.getGreen(),
				col.getBlue(), col.getOpacity()));
		col = (Color) polygon.getStroke();
		newObject.polygon.setStroke(new Color(col.getRed(), col.getGreen(),
				col.getBlue(), col.getOpacity()));
		newObject.polygon.setStrokeWidth(polygon.getStrokeWidth());
		return newObject;
	}

	@Override
	public ShapeProperties getShapeProperties() {
		ShapeProperties prop = new RectangleProperties();
		prop.setFillColor(polygon.getFill());
		prop.setStrokeColor(polygon.getStroke());
		try {
			prop.setPoint1(upperLeftPoint.clone());
			prop.setPoint2(new Point(upperLeftPoint.getX() + width,
					upperLeftPoint.getY() + height));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		prop.setStrokeWidth(polygon.getStrokeWidth());
		prop.setRotation(polygon.getRotate());
		prop.setTranslateX(polygon.getTranslateX());
		prop.setTranslateY(polygon.getTranslateY());
		return prop;
	}
}

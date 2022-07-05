public class MetodaHookeaJeevesa {

    private Point x0 = new Point(1, 1);
    private Point xB0 = new Point(1, 1);
    private Point xB = new Point(1, 1);
    private Point x1, x2;
    private double f0, fb, f;
    int iter;
    private int j;
    private double e = 0.5;
    private double beta = 0.5;
    private double epsilon = 0.0039;
    private int n = 2;

    private double f(double x, double y) {
        return Math.pow(x, 3) + 5 * Math.pow(y, 2) - 6 * x * y - y + 7; //moj

    }

    //funckje pomocnicze
    private double[] vector(int j) {
        switch (j) {
            case 1 -> {
                return new double[]{1, 0};
            }
            case 2 -> {
                return new double[]{0, 1};
            }
            default -> {
                return null;
            }
        }
    }

    private Point x(int j) {
        return switch (j) {
            case 0 -> x0;
            case 1 -> x1;
            case 2 -> x2;
            default -> null;
        };
    }

    private double[] multiplicationScalarVector(double scalar, double[] vector) {
        var solution = new double[2];
        for (int i = 0; i < 2; i++) {
            solution[i] = scalar * vector[i];
        }
        return solution;
    }

    private Point multiplicationScalarPoint(double scalar, Point point) {
        return new Point(scalar * point.getX(), scalar * point.getY());
    }

    private Point additionPointPoint(Point point1, Point point2) {
        return new Point(point1.getX() + point2.getX(), point1.getY() + point2.getY());
    }

    private Point subtractionPointPoint(Point point1, Point point2) {
        return new Point(point1.getX() - point2.getX(), point1.getY() - point2.getY());
    }

    public Point additionPointVector(Point point, double[] vector) {
        return new Point(point.getX() + vector[0], point.getY() + vector[1]);
    }

    public Point subtractionPointVector(Point point, double[] vector) {
        return new Point(point.getX() - vector[0], point.getY() - vector[1]);
    }

    //funckje robocze
    //////////////etap probny/////////////////
    private void etapProbnyKrok1() {

        j = 1;
        f0 = f(x0.getX(), x0.getY());
        fb = f(xB.getX(), xB.getY());
        etapProbnyKrok2();
    }

    private void etapProbnyKrok2() {

        System.out.println(++iter);
        switch (j) {
            case 1 -> {
                x1 = additionPointVector(x(j - 1), multiplicationScalarVector(e, vector(j)));
                f = f(x1.getX(), x1.getY());
            }
            case 2 -> {
                x2 = additionPointVector(x(j - 1), multiplicationScalarVector(e, vector(j)));
                f = f(x2.getX(), x2.getY());
            }
        }
        etapProbnyKrok3();
    }

    private void etapProbnyKrok3() {

        if (f < f0) {
            f0 = f;
            etapProbnyKrok5();
        } else {
            switch (j) {
                case 1 -> {
                    x1 = subtractionPointVector(x(j), multiplicationScalarVector(2 * e, vector(j)));
                    f = f(x1.getX(), x1.getY());
                }
                case 2 -> {
                    x2 = subtractionPointVector(x(j), multiplicationScalarVector(2 * e, vector(j)));
                    f = f(x2.getX(), x2.getY());
                }
            }
            etapProbnyKrok4();
        }
    }

    private void etapProbnyKrok4() {

        if (f < f0) {
            f0 = f;
        } else {
            switch (j) {
                case 1 -> {
                    x1 = additionPointVector(x(j), multiplicationScalarVector(e, vector(j)));
                    f = f(x1.getX(), x1.getY());
                }
                case 2 -> {
                    x2 = additionPointVector(x(j), multiplicationScalarVector(e, vector(j)));
                    f = f(x2.getX(), x2.getY());
                }
            }
        }
        etapProbnyKrok5();
    }

    private void etapProbnyKrok5() {
        if (j != n) {
            j = j + 1;
            etapProbnyKrok2();
        } else {

            if (fb > f0) {
                etapRoboczyKrok1();
            }
            else {
                if (e > epsilon) {

                    if (iter > 1) {

                        e = beta * e;
                        x0 = xB;
                        etapProbnyKrok1();
                    }
                } else {
                    System.out.println(x0);
                }
            }

        }
    }

    private void etapRoboczyKrok1() {

        xB0 = new Point(xB.getX(),xB.getY());
        switch (j) {
            case 1:
                xB = new Point(x1.getX(),x1.getY());
                break;
            case 2:
                xB = new Point(x2.getX(),x2.getY());
                break;
        }
        etapRoboczyKrok2();
    }

    private void etapRoboczyKrok2() {

//        x0 = additionPointPoint(xB, subtractionPointPoint(xB, xB0));
        x0 = subtractionPointPoint(multiplicationScalarPoint(2,xB),xB0);
        etapRoboczyKrok3();
    }

    private void etapRoboczyKrok3() {

        etapProbnyKrok1();
    }

    public void run() {
        etapProbnyKrok1();
    }
}

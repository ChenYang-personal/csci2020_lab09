package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Main extends Application {
    double google_stock[];
    double apple_stock[];

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("lab09: Stock Performance");
        apple_stock=downloadStockPrices("AAPL");
        google_stock=downloadStockPrices("GOOG");
        drawLinePlot();
        primaryStage.setScene(new Scene(new Pane(canvas), 1000, 1000));
        primaryStage.show();
    }

    public void plotLine(Color color,double stock[],GraphicsContext gc){
        for(int i=0;i<71;i++){
            double a = 50+i*12;
            double b = 950 - stock[i];
            double c = 50+(i+1)*12;
            double d = 950 - stock[i+1];
            gc.setStroke(color);
            gc.strokeLine(a,b,c,d);
            gc.stroke();

        }
    }
    public double[] downloadStockPrices(String company) throws IOException {
        String website = "https://query1.finance.yahoo.com/v7/finance/download/" +
                company+"?period1=1262322000&period2=1451538000&interval=1mo&" +
                "events=history&includeAdjustedClose=true";
        URL download_data = new URL(website);
        URLConnection data = download_data.openConnection();
        Scanner write = new Scanner(data.getInputStream());


        double stock_data[]=new double[80];
        boolean hasnext = true;
        int index=0;
        while (write.hasNext()){
            if(hasnext == true){
                write.nextLine();
                hasnext=false;
            }
            if(hasnext == false){
                String list[]=write.nextLine().split(",");
                stock_data[index] =Double.parseDouble(list[5]);
                index++;
            }
        }
        return stock_data;
    }

    Canvas canvas = new Canvas(1000,1000);
    public void drawLinePlot(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeLine(50,950,950,950);
        gc.strokeLine(50,50,50,950);
        gc.stroke();
        plotLine(Color.BLUE,apple_stock,gc);
        plotLine(Color.RED,google_stock,gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
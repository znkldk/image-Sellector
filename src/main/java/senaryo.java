public class senaryo {

    public void basicSenaryo() throws Exception {
        methods Methods= new methods();
        Methods.setUpreferanceNumbers("automated.png");

        Methods.clickOnImage("automated.png");
        Methods.clickOnImage("Company.png");
        Methods.clickOnImage("Pricing.png");
        Methods.clickOnImage("Services.png");

        Methods.takeScreenShot();

    }


}

public class Main {
    public static void main(String[] args) {
        String tmp = "±±±222±±±±2227722±±±";
        System.out.println(Algorithm.compress(tmp));
        System.out.println(Algorithm.decompress(Algorithm.compress(tmp)));
    }
}
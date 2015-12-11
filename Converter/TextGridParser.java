public class TextGridParser {

    private ArrayList<Interval> intervals;
    private int size;

    public void parse(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String aux, text;
        double min, max;
        String[] splittedLine;

        for (int i = 1; i <= 13; i++) {
            scanner.nextLine();
        }

        aux = scanner.nextLine();
        splittedLine = aux.split(" ");
        size = parseInt(splittedLine[splittedLine.length - 1]);

        for (int i = 1; i <= size; i++) {
            scanner.nextLine();

            aux = scanner.nextLine();
            splittedLine = aux.split(" ");
            min = parseDouble(splittedLine[splittedLine.length - 1]);

            aux = scanner.nextLine();
            splittedLine = aux.split(" ");
            max = parseDouble(splittedLine[splittedLine.length - 1]);

            aux = scanner.nextLine();
            splittedLine = aux.split(" ");
            text = splittedLine[splittedLine.length - 1];

            Interval interval = new Interval(text, min, max);
            intervals.add(interval);
        }
    }
}

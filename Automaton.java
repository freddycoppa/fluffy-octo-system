import java.util.Random;

public class Automaton {
	private int width, height;
	private int[] buffer0, buffer1;
	Random random;

	public Automaton(int width, int height, int[] buffer0) {
		this.width = width;
		this.height = height;
		this.buffer0 = buffer0;
		this.buffer1 = new int[width * height];
		this.random = new Random();
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				int r = random.nextInt();
				buffer0[y * width + x] = r;
				buffer1[y * width + x] = r;
			}
	}

	public void nextIteration() {
		buffer0[0] = (buffer1[0] + buffer1[width - 1] + buffer1[1] + buffer1[(height - 1) * width] + buffer1[width]
				+ buffer1[(height - 1) * width + width - 1] + buffer1[2 * width - 1] + buffer1[(height - 1) * width + 1]
				+ buffer1[width + 1]) / 9;
		for (int x = 1; x < width - 1; x++) {
			buffer0[x] = (buffer1[x] + buffer1[x - 1] + buffer1[x + 1] + buffer1[(height - 1) * width + x]
					+ buffer1[width + x] + buffer1[(height - 1) * width + x - 1] + buffer1[width + x - 1]
					+ buffer1[(height - 1) * width + x + 1] + buffer1[width + x + 1]) / 9;
			for (int y = 1; y < height - 1; y++) {
				buffer0[y * width + x] = (buffer1[y * width + x] + buffer1[y * width + x - 1]
						+ buffer1[y * width + x + 1] + buffer1[(y - 1) * width + x] + buffer1[(y + 1) * width + x]
						+ buffer1[(y - 1) * width + x - 1] + buffer1[(y + 1) * width + x - 1]
						+ buffer1[(y - 1) * width + x + 1] + buffer1[(y + 1) * width + x + 1]) / 9;
			}
			buffer0[(height - 1) * width + x] = (buffer1[(height - 1) * width + x]
					+ buffer1[(height - 1) * width + x - 1] + buffer1[(height - 1) * width + x + 1]
					+ buffer1[(height - 2) * width + x] + buffer1[x] + buffer1[(height - 2) * width + x - 1]
					+ buffer1[x - 1] + buffer1[(height - 2) * width + x + 1] + buffer1[x + 1]) / 9;
		}
		buffer0[(height - 1) * width + width
				- 1] = (buffer1[(height - 1) * width + width - 1] + buffer1[(height - 1) * width + width - 2]
						+ buffer1[(height - 1) * width] + buffer1[(height - 2) * width + width - 1] + buffer1[width - 1]
						+ buffer1[(height - 2) * width + width - 2] + buffer1[width - 2] + buffer1[(height - 2) * width]
						+ buffer1[0]) / 9;
		int[] buffer = buffer0;
		buffer0 = buffer1;
		buffer1 = buffer;
	}
}

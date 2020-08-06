package renderer;

/**
 * Pixel is an helper class whose objects are associated with a Render object that
 * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
 * its progress.<br/>
 * There is a main follow up object and several secondary objects - one in each thread.
 */
class Pixel {
    private boolean _debug = false;

    private long _maxRows = 0; // Ny
    private long _maxCols = 0; // Nx
    private long _pixels = 0; // Total number of pixels: Nx*Ny
    public volatile int row = 0; // Last processed row
    public volatile int col = -1; // Last processed column
    private long _counter = 0; // Total number of pixels processed
    private int _percents = 0; // Percent of pixels processed
    private long _nextCounter = 0; // Next amount of processed pixels for percent progress

    /**
     * The constructor for initializing the main follow up Pixel object
     *
     * @param maxRows the amount of pixel rows
     * @param maxCols the amount of pixel columns
     */
    public Pixel(int maxRows, int maxCols) {
        _maxRows = maxRows;
        _maxCols = maxCols;
        _pixels = maxRows * maxCols;
        _nextCounter = _pixels / 100;
    }

    /**
     * The constructor for initializing the main follow up Pixel object
     *
     * @param maxRows the amount of pixel rows
     * @param maxCols the amount of pixel columns
     */
    public Pixel(int maxRows, int maxCols, boolean _debug) {
        _maxRows = maxRows;
        _maxCols = maxCols;
        _pixels = maxRows * maxCols;
        _nextCounter = _pixels / 100;
        if (_debug) System.out.printf("\r %02d%%", _percents);

    }

    /**
     * Default constructor for secondary Pixel objects
     */
    public Pixel() {
    }

    /**
     * Public function for getting next pixel number into secondary Pixel object.
     * The function prints also progress percentage in the console window.
     * @param target target secondary Pixel object to copy the row/column of the next pixel
     * @return true if the work still in progress, -1 if it's done
     */
    public boolean nextPixel(Pixel target) {
        int percents = nextP(target);
        if (_debug && percents > 0) System.out.printf("\r %02d%%", percents);
        if (percents >= 0) return true;
        if (_debug) System.out.printf("\r %02d%%", 100);
        return false;
    }

    /**
     * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
     * critical section for all the threads, and main Pixel object data is the shared data of this critical
     * section.<br/>
     * The function provides next pixel number each call.
     * @param target target secondary Pixel object to copy the row/column of the next pixel
     * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
     * finished, any other value - the progress percentage (only when it changes)
     */
    private synchronized int nextP(Pixel target) {
        ++col; ++_counter;
        if (col < _maxCols) {
            target.row = this.row; target.col = this.col;
            if (_debug && _counter == _nextCounter) {
                ++_percents;_nextCounter = _pixels * (_percents + 1) / 100; return _percents;
            }
            return 0;
        }
        ++row;
        if (row < _maxRows) {
            col = 0;
            if (_debug && _counter == _nextCounter) {
                ++_percents; _nextCounter = _pixels * (_percents + 1) / 100; return _percents;
            }
            return 0;
        }
        return -1;
    }

    /**
     * Set debug printing on
     * @return the Render object itself
     */
    public Pixel setDebugPrint() { _debug = true; return this; }
}

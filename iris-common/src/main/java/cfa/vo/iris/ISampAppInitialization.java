package cfa.vo.iris;

public interface ISampAppInitialization {
    /**
     * Return whether or not SAMP was enabled for this session. SAMP features could be required to be switched
     * on and off when the application is launched.
     *
     * @return True if SAMP is enabled in this session.
     */
    boolean isSampEnabled();

    /**
     * Return an object capable of sending and receiving SAMP messages.
     * @return
     */
    ISampManager getSampManager();
}

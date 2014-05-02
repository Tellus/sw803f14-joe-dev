package dk.homestead.sw803f14.service;

interface ISunWukongTransferListener {
    void onComplete(String path);
    void onProgress(int current, int max);
}
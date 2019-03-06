package server;


interface MessageHandler extends Runnable {

        void handle(MessageBox messageBox);
}

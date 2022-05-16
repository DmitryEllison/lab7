package dimka.blinb.collection.interfaces;

import dimka.blinb.collection.utilities.Notification;

/**
 * ����������� ������� ����������������� ������ ������������ ������ � �����������
 * �� ����������� ���� ���������� ������� (ICommand)
 */
public interface ICommandDispatcher{
    public Notification handle(ICommand cmd) throws Exception;
}

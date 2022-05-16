package dimka.blinb.collection.interfaces;

/**
 * ����������� ������� ����������������� ������ ������������ ������ � �����������
 * �� ����������� ���� ���������� ������� (ICommand)
 */
public interface ICommandDispatcher{
    public <TCommand extends ICommand> void Execute(TCommand command);
}

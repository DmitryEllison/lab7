package dimka.blinb.collection.interfaces;

/**
 * определение способа централизованного вызова обработчиков команд в зависимости
 * от конкретного типа переданной команды (ICommand)
 */
public interface ICommandDispatcher{
    public <TCommand extends ICommand> void Execute(TCommand command);
}

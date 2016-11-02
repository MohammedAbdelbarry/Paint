package paint.data.util;

import java.util.Stack;

import javafx.scene.canvas.Canvas;

public class History {
	private Stack<HistoryEvent> undoStack;
	private Stack<HistoryEvent> redoStack;
	private HistoryEvent currentEvent;
	private static History history = new History();

	private History() {
		undoStack = new Stack<>();
		redoStack = new Stack<>();
	}

	public static History getHistory() {
		return history;
	}

	public static void clearHistory() {
		history = new History();
	}

	public HistoryEvent undo(Canvas canvas) {
		System.out.println("UNDO: " + undoStack.size());

		if (undoStack.isEmpty()) {
			System.out.println("" + undoStack.size());
			return null;
		} else {
			System.out.println("VALID UNDO");
			undoStack.peek().showEvent(canvas);
			redoStack.push(currentEvent);
			currentEvent = undoStack.pop();
			try {
				return currentEvent.clone();
			} catch (CloneNotSupportedException e1) {
				return null;
			}
		}
	}

	public HistoryEvent redo(Canvas canvas) {
		System.out.println("REDO: " + redoStack.size());
		if (redoStack.isEmpty()) {
			return null;
		} else {
			redoStack.peek().showEvent(canvas);
			undoStack.push(currentEvent);
			currentEvent = redoStack.pop();
			try {
				return currentEvent.clone();
			} catch (CloneNotSupportedException e1) {
				return null;
			}
		}
	}


	public void storeShapeChanges(HistoryEvent current) {
		HistoryEvent head = new HistoryEvent();
		try {
			head = current.clone();
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
		}

		if (currentEvent != null) {
			undoStack.push(currentEvent);
		}
		currentEvent = head;
		redoStack.clear();
		System.out.println("STORING SHAPE: " + undoStack.size());
	}

}
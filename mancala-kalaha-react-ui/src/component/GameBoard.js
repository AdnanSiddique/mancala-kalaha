import PitButton from "./PitButton";
import MovesComponent from "./Moves";

const GameBoard = ({ game, handleMove, aLastPit, bLastPit, aHousePit, bHousePit }) => {
    const renderedAPits = renderPits('A', game, aLastPit, aHousePit, handleMove);
    const renderedBPits = renderPits('B', game, bLastPit, aHousePit, handleMove);

    return (
        <div>
            <div className={'alert'}>
                <p>Please note the following rules for playing game with 2 players (Player A and Player B).</p>
                <ul>
                    <li>
                        <p className={'player-a-text'}>Red color controls for Player : A</p>
                    </li>
                    <li>
                        <p className={'player-b-text'}>Blue color controls for Player : B</p>
                    </li>
                </ul>
            </div>
            <div className={'board-div'}>
                <div>
                    <PitButton disabled={true} className={'player-b-pit board-house-pit'} stones={game.pits[bHousePit]} />
                </div>
                <div className={'player-pits-div'}>
                    <div>{renderedBPits}</div>
                    <div>{renderedAPits}</div>
                </div>
                <div>
                    <PitButton disabled={true} className={'player-a-pit board-house-pit'} stones={game.pits[aHousePit]} />
                </div>
            </div>
            <div style={{marginTop: '30px'}}>
                <hr className="dashed"/>
                <h3 className={`border ${game.currentPlayer === 'A' ? 'linear-repeating-A' : 'linear-repeating-B'}`} >Turn of Player : {game.currentPlayer}</h3>
            </div>

            <ul>
                {
                    game.moves && game.moves.length > 0 ?
                        <div>
                            <h1>History</h1>
                            <MovesComponent moves={game.moves} />
                        </div>
                    : ''
                }
            </ul>

        </div>
    );
};

const renderPits = (player, game, lastPit, housePit, handleMove) => {
    const renderedPits = [];
    for (let key = player === 'A' ? 0 : lastPit; player === 'A' ? key <= lastPit : key >= housePit + 1; player === 'A' ? key++ : key--) {
        const stones = game.pits[key];
        const currentPlayer = game.currentPlayer;
        renderedPits.push(
            <PitButton
                    disabled={currentPlayer === (player === 'A' ? 'B' : 'A')}
                    className={`pit player-${player.toLowerCase()}-pit`}
                    key={key}
                    onClick={() => handleMove(key)}
                    stones={stones}
                >
            </PitButton>
        );
    }
    return renderedPits;
};

export default GameBoard;

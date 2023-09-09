# Adelson-Velsky and Lanis Tree

---

## AVL Tree의 특성

- 이진 검색 트리의 모든 특성을 가짐.
- 노드의 두 하위 트리 (왼쪽 자식, 오른쪽 자식)의 높이 차이가 최대 1을 넘지 않음.
- 4가지 회전(LL, RR, LR, RL) 연산을 통해 트리의 균형을 유지함.

## 구현해야 하는 메서드

- boolean add(int value)
  - value의 값을 트리에 추가
  - 이미 값이 존재한다면 추가하지 않음 (중복을 허용하지 않음)
  - add 성공 시 true 반환, 중복 값이 있다면 false 반환
- boolean remove(int value)
  - value의 값을 트리에서 찾아 삭제
  - remove 성공 시 true 반환, 그렇지 않으면 false 반환
- int size()
  - 트리의 요소 개수 반환
- boolean isEmpty()
  - 트리가 비었는지 여부를 반환
- boolean contains(int value)
  - 트리가 value를 요소로 가지고 있는지 여부를 반환
- void clear()
  - 트리 요소를 전부 삭제
  - 반환값 없음
- void print()
  - 트리의 모습을 출력